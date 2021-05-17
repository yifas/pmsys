package com.bin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.ScriptDao;
import com.bin.dao.TaskDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryScriptDto;
import com.bin.pojo.GroupScript;
import com.bin.pojo.Script;
import com.bin.pojo.Task;
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

    @PostMapping("/getTask")
    public Result getTask(@RequestBody PageQueryBean param) {
        Page<Task> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        IPage<Task> list = taskDao.selectPage(page, null);

        return new Result(200, "请求成功", list);
    }

    /**
     * 用于构建前端Select
     * @return
     */
    @GetMapping("/getSelect")
    public Result getSelect() {
        //查询所有script
        List<Script> scripts = scriptDao.selectList(null);
        List list = new ArrayList();
        //取出Id 和 name
        for (Script script : scripts) {
            Map<String,Object> map = new HashMap<>();
            map.put("value",script.getId());
            map.put("label",script.getName());
            list.add(map);
        }

        return new Result(200, "查询成功",list);
    }

    @PostMapping("/addTask")
    public Result addTask(@RequestBody QueryScriptDto script) {
        /*
            1.怎么接受 select中的值
            2.存储到task_group关系表
            3.
         */



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
}
