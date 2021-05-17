package com.bin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.GroupTaskDao;
import com.bin.dao.ScriptDao;
import com.bin.dao.TaskDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryScriptDto;
import com.bin.dto.QueryTaskDto;
import com.bin.pojo.GroupScript;
import com.bin.pojo.GroupTask;
import com.bin.pojo.Script;
import com.bin.pojo.Task;
import com.bin.service.GroupTaskService;
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
            3.
         */
        //scriptID=taskDto.getScriptName()
        Script script = scriptDao.selectById(taskDto.getScriptName());
        Task task = new Task();
        BeanUtils.copyProperties(taskDto, task);
        task.setScriptName(script.getName());
        task.setCreateTime(new Date());

        taskDao.insert(task);

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

}
