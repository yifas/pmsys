package com.bin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.GroupDao;
import com.bin.dao.GroupDeviceDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryGroupCondition;
import com.bin.pojo.Group;
import com.bin.pojo.GroupDevice;
import com.bin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/device/group")
public class GroupController {


    @Autowired
    private GroupDao groupDao;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupDeviceDao groupDeviceDao;

    @PostMapping("/getGroup")
    public Result getGroup(@RequestBody PageQueryBean param) {
        /*
            遍历组  遍历设备 统计数值  插入数据库
            消耗性能
         */
        //1.获取所有组
        List<Group> groups = groupDao.selectList(null);
        for (Group group : groups) {
            //1.1获取每个分组下在线的设备
            Integer num = groupService.listDevice(group.getId());
            group.setOnline(num);
            //UpdateWrapper<Group> wrapper = new UpdateWrapper<>();
            groupDao.updateById(group);
        }


        Page<Group> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        IPage<Group> list = groupDao.selectPage(page,null);

        return new Result(200, "请求成功", list);
    }


    @PostMapping("/addGroup")
    public Result addGroup(@RequestBody Group group) {
        //创建新增时间
        group.setCreateTime(new Date());
        groupDao.insert(group);
        return new Result(200, "新增成功");
    }



    @GetMapping("/getAllGroup")
    public Result getAllGroup() {
        return new Result(200, "查询成功", groupDao.selectList(null));
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
        //删除了group 还要删除关系表
        groupDao.deleteById(id);
        //删除关系表
        QueryWrapper<GroupDevice> wrapper=new QueryWrapper<>();
        wrapper.eq("groupId",id);
        groupDeviceDao.delete(wrapper);

        return new Result(200, "删除成功");
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
        return new Result(200, "查询成功", groups);
    }

    /**
     * 批量删除
     * @param ids id数组
     * @return
     */
    @PostMapping("/deleteGroupByIds")
    public Result deleteGroupByIds(@RequestBody Long[] ids) {

        //数组转集合（重新构造新的ArrayList）
        groupDao.deleteBatchIds(new ArrayList<>(Arrays.asList(ids)));

        return new Result(200, "删除成功");
    }

    @PostMapping("/setGroup")
    public Result setGroup(@RequestBody HashMap map) {

        groupService.setGroup(map);
        return new Result(200, "设置分组成功");
    }


}
