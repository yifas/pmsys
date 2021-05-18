package com.bin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.GroupDao;
import com.bin.dao.GroupTaskDao;
import com.bin.dao.ScriptDao;
import com.bin.dao.TaskDao;
import com.bin.dto.*;
import com.bin.pojo.Group;
import com.bin.pojo.GroupTask;
import com.bin.pojo.Script;
import com.bin.pojo.Task;
import com.bin.service.GroupTaskService;
import com.bin.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/device/task")
public class TaskController {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ScriptDao scriptDao;

    @Autowired
    private GroupTaskDao groupTaskDao;

    @Autowired
    private GroupTaskService groupTaskService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private GroupDao groupDao;

    @PostMapping("/getTask")
    public Result getTask(@RequestBody PageQueryBean param) {
        Page<Task> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        IPage<Task> list = taskDao.selectPage(page, null);

        return new Result(200, "请求成功", list);
    }

    /**
     * 用于构建前端Select
     *
     * @return
     */
    @GetMapping("/getSelect")
    public Result getSelect() {
        //查询所有script
        List<Script> scripts = scriptDao.selectList(null);
        List list = new ArrayList();
        //取出Id 和 name
        for (Script script : scripts) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", script.getId());
            map.put("label", script.getName());
            list.add(map);
        }

        return new Result(200, "查询成功", list);
    }

    @PostMapping("/addTask")
    public Result addTask(@RequestBody QueryTaskDto taskDto) {
        /*
            1.怎么接受 select中的值
            2.存储到task_group关系表
            3.插入时需要判断时间段 组对应的时间段
         */
        //scriptID=taskDto.getScriptName()
        Script script = scriptDao.selectById(taskDto.getScriptName());
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        task.setScriptName(script.getName());
        //task.setCreateTime(new Date());
        //根据groupid查询group name  存储到task
        for (Integer i : taskDto.getCheckList()) {
            Group group = groupDao.selectById(i);
            task.setGroupName(group.getName());
        }
        //需要判断当前group 对应的task 中时间段
        

        taskDao.insert(task);

        //实际已经是一对一了  但是还是维护了一张关系表
        if (taskDto.getCheckList() != null) {
            for (Integer i : taskDto.getCheckList()) {
                GroupTask groupTask = new GroupTask();
                groupTask.setTaskId(task.getId());
                groupTask.setGroupId(i);
                groupTaskDao.insert(groupTask);
            }
        }
        //System.out.println(script.getCheckList());
      /*  //1.存储到script表
        script.setCreateTime(new Date());
        scriptDao.insert(script);
        //2.存储到关系表
        if (script.getCheckList()!=null){
            for (Integer i : script.getCheckList()) {
                GroupScript groupScript = new GroupScript();
                groupScript.setScriptId(script.getId());
                groupScript.setGroupId(i);
                groupScriptDao.insert(groupScript);
            }
        }*/

        return new Result(200, "新增成功");
    }


    @GetMapping("/getTaskById/{id}")
    public Result getTaskById(@PathVariable Long id) {

        Task task = taskDao.selectById(id);
        QueryTaskDto queryTaskDto = new QueryTaskDto();
        BeanUtils.copyProperties(task, queryTaskDto);
        /*QueryWrapper<GroupTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("groupId").eq("taskId",id);
        List<GroupTask> tasks = groupTaskDao.selectList(queryWrapper);*/
        Integer[] ids = groupTaskService.getGroupIds(id);
        queryTaskDto.setCheckList(ids);

        return new Result(200, "查询成功", queryTaskDto);
    }

    @PutMapping("/updateTaskById/{id}")
    public Result updateTaskById(@PathVariable Integer id, @RequestBody QueryTaskDto taskDto) {
        /*
            1.修改script name
            2.重新建立关系
         */
        Task task = new Task();
        BeanUtils.copyProperties(taskDto,task);
        task.setId(id);
        task.setUpdateTime(new Date());
        //根据groupid查询group name  存储到task
        for (Integer i : taskDto.getCheckList()) {
            Group group = groupDao.selectById(i);
            task.setGroupName(group.getName());
        }

        taskDao.updateById(task);
        //删除原有关系
        QueryWrapper<GroupTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("taskId",id);
        groupTaskDao.delete(queryWrapper);
        //重新建立关系
        if (taskDto.getCheckList()!=null){
            for (Integer i : taskDto.getCheckList()) {
                GroupTask groupTask = new GroupTask();
                groupTask.setTaskId(id);
                groupTask.setGroupId(i);
                groupTaskDao.insert(groupTask);
            }
        }else {
            return new Result(401,"重建group_task关系失败");
        }
        //scriptService.updateRelation(id,script.getCheckList());
        return new Result(200, "修改成功");
    }

    //获取脚本名字  但返回的是脚本的ID
    @GetMapping("/getSelectById/{id}")
    public Result getSelectById(@PathVariable Integer id){
        //id是task的id
        Task task = taskDao.selectById(id);
        if (task!=null){
            QueryWrapper<Script> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",task.getScriptName());
            Script script = scriptDao.selectOne(queryWrapper);
            if (script!=null){

                return new Result(200,"查询成功",script.getId());
            }
        }
        return new Result(401,"查询失败");
    }


    @DeleteMapping("/deleteTaskById/{id}")
    public Result deleteTaskById(@PathVariable Long id) {
        //删除了task 还要删除关系表
        taskDao.deleteById(id);
        //删除关系表
        QueryWrapper<GroupTask> wrapper=new QueryWrapper<>();
        wrapper.eq("taskId",id);
        groupTaskDao.delete(wrapper);

        return new Result(200, "删除成功");
    }


    @PostMapping("/getTaskByCond")
    public Result getTaskByCond(@RequestBody QueryTaskCondition condition) {
        //多条件查询
        Map<String, Object> map = new HashMap<>();

        if ((!"".equals(condition.getScriptName()))&& condition.getScriptName()!=null) {

            map.put("scriptName", condition.getScriptName());
        }
        if ((!"".equals(condition.getCreateTime())) && condition.getCreateTime()!=null) {

            map.put("createTime", condition.getCreateTime());
        }
        if ((!"".equals(condition.getSerial())) && condition.getSerial()!=null) {

            map.put("serial", condition.getSerial());
        }
        if ((!"".equals(condition.getRemark())) && condition.getRemark()!=null) {

            map.put("remark", condition.getRemark());
        }
        if ( condition.getStatus()!=null) {

            map.put("status", condition.getStatus());
        }

        List<Task> tasks = taskService.selectByMapCond(map);

      /*  Device device = new Device();
        BeanUtils.copyProperties(condition,device);*/
        return new Result(200, "查询成功",tasks);
    }


}
