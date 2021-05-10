package com.bin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.GroupDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryCondition;
import com.bin.dto.QueryGroupCondition;
import com.bin.pojo.Device;
import com.bin.pojo.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device/group")
public class GroupController {


    @Autowired
    private GroupDao groupDao;


    @PostMapping("/getGroup")
    public Result getGroup(@RequestBody PageQueryBean param) {
        Page<Group> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        IPage<Group> list = groupDao.selectPage(page,null);
        return new Result(200, "请求成功", list);
    }


    @GetMapping("/getGroupById/{id}")
    public Result getGroupById(@PathVariable Long id) {
        return new Result(200, "查询成功", groupDao.selectById(id));
    }

    @PutMapping("/updateGroupById/{id}")
    public Result updateGroupById(@PathVariable Integer id, @RequestBody Group group) {
        group.setId(id);
        return new Result(200, "修改成功", groupDao.updateById(group));
    }


    @DeleteMapping("/deleteGroupById/{id}")
    public Result deleteGroupById(@PathVariable Long id) {

        return new Result(200, "删除成功", groupDao.deleteById(id));
    }

    @PostMapping("/getGroupByCond")
    public Result getGroupByCond(@RequestBody QueryGroupCondition condition) {
        //多条件查询
        Map<String, Object> map = new HashMap<>();

        //todo 待优化
        if (condition.getId()!=null) {
            map.put("id", condition.getId());
        }
        if (!"".equals(condition.getName())) {

            map.put("name", condition.getName());
        }
        if (!"".equals(condition.getRemark())) {

            map.put("remark", condition.getRemark());
        }
        List<Group> groups = groupDao.selectByMap(map);
      /*  Device device = new Device();
        BeanUtils.copyProperties(condition,device);*/
        return new Result(200, "请求成功", groups);
    }

    /**
     * 批量删除
     * @param ids id数组
     * @return
     */
    @PostMapping("/deleteGroupByIds")
    public Result deleteGroupByIds(@RequestBody Long[] ids) {



        return new Result(200, "请求成功");
    }
}
