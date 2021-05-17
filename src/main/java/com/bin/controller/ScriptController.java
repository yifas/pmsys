package com.bin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.GroupScriptDao;
import com.bin.dao.ScriptDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryScriptCondition;
import com.bin.dto.QueryScriptDto;
import com.bin.pojo.GroupScript;
import com.bin.pojo.Script;
import com.bin.service.GroupScriptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/device/script")
public class ScriptController {


    @Autowired
    private ScriptDao scriptDao;



    @Autowired
    private GroupScriptDao groupScriptDao;

    @Autowired
    private GroupScriptService scriptService;



    @PostMapping("/getScript")
    public Result getScript(@RequestBody PageQueryBean param) {
        Page<Script> page = new Page<>(param.getCurrentPage(), param.getPageSize());


        IPage<Script> list = scriptDao.selectPage(page, null);


        return new Result(200, "请求成功", list);
    }



    @PostMapping("/addScript")
    public Result addScript(@RequestBody QueryScriptDto script) {

        //System.out.println(script.getCheckList());
        //1.存储到script表
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
        }

        return new Result(200, "新增成功");
    }


    @GetMapping("/getScriptById/{id}")
    public Result getScriptById(@PathVariable Long id) {

        Script script = scriptDao.selectById(id);
        QueryScriptDto queryScriptDto = new QueryScriptDto();
        BeanUtils.copyProperties(script,queryScriptDto);
        queryScriptDto.setCheckList(scriptService.getGroupsByScriptId(id));
        //queryScriptDto.setCheckList(new Integer[] {1,2,3});
        return new Result(200, "查询成功", queryScriptDto);
    }



    @PutMapping("/updateScriptById/{id}")
    public Result updateScriptById(@PathVariable Integer id, @RequestBody QueryScriptDto script) {
        script.setId(id);
        scriptDao.updateById(script);
        //重新建立关系
        scriptService.updateRelation(id,script.getCheckList());
        return new Result(200, "修改成功");
    }

    @GetMapping("/getGroupsByScriptId/{id}")
    public Result getGroupsByScriptId(@PathVariable Long id) {

        Integer[] ids = scriptService.getGroupsByScriptId(id);
        return new Result(200, "查询成功", ids);
    }



    @DeleteMapping("/deleteScriptById/{id}")
    public Result deleteScriptById(@PathVariable Long id) {
        //删除了group 还要删除关系表
        scriptDao.deleteById(id);
        //删除关系表
        QueryWrapper<GroupScript> wrapper=new QueryWrapper<>();
        wrapper.eq("scriptId",id);
        groupScriptDao.delete(wrapper);

        return new Result(200, "删除成功");
    }


    @PostMapping("/getScriptByCond")
    public Result getScriptByCond(@RequestBody QueryScriptCondition condition) {
        //多条件查询
        Map<String, Object> map = new HashMap<>();

        if ((!"".equals(condition.getName()))&& condition.getName()!=null) {

            map.put("name", condition.getName());
        }
        if ((!"".equals(condition.getAppName())) && condition.getAppName()!=null) {

            map.put("appName", condition.getAppName());
        }
        if ((!"".equals(condition.getRemark())) && condition.getRemark()!=null) {

            map.put("remark", condition.getRemark());
        }
        List<Script> scripts = scriptDao.selectByMap(map);
      /*  Device device = new Device();
        BeanUtils.copyProperties(condition,device);*/
        return new Result(200, "查询成功", scripts);
    }



    /*@GetMapping("/getAllGroup")
    public Result getAllGroup(){

        QueryWrapper<Group> queryWrapper = new QueryWrapper<>();
        queryWrapper.
        groupDao.selectList();
        return new Result(200, "请求成功");
    }*/

}
