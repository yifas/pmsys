package com.bin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.DeviceDao;
import com.bin.dao.GroupDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryCondition;
import com.bin.dto.StatisticsVo;
import com.bin.pojo.Device;
import com.bin.pojo.Group;
import com.bin.service.DeviceService;
import com.bin.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date
 */

@RestController
@RequestMapping("/device")
public class DeviceController {


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private InfoService infoService;

    @Autowired
    private GroupDao groupDao;

    /**
     * 获取请求数据
     *
     * @param device
     * @return
     */
    @PostMapping("/addDevice")
    public Result addDevice(@RequestBody Device device) {

        return deviceService.addDevice(device);
    }

    @PostMapping("/getDevice")
    public Result getDevice(@RequestBody PageQueryBean param) {
        //分页查询前对updateTime进行判断 并对>60s设置为离线 在Info表添加离线记录
        List<Device> devices = deviceService.selectIds();
        int i = deviceService.updateByCurrentTime();
        if (i>0){
            //更新成功 在Info表添加离线记录

            infoService.insertInfo(devices);
        }

        //分页查询
        Page<Device> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        IPage<Device> list = deviceDao.selectPage(page, null);
        //System.out.println(list);
        //List<Device> devices = deviceDao.selectList(null);
        return new Result(200, "请求成功", list);
    }

    @GetMapping("/getDeviceById/{id}")
    public Result getDeviceById(@PathVariable Long id) {
        return new Result(200, "查询成功", deviceDao.selectById(id));
    }

    @PutMapping("/updateDeviceById/{id}")
    public Result updateDeviceById(@PathVariable Integer id, @RequestBody Device device) {
        device.setId(id);
        return new Result(200, "修改成功", deviceDao.updateById(device));
    }

    @PostMapping("/getDeviceByCond")
    public Result getDeviceByCond(@RequestBody QueryCondition condition) {
        //多条件查询
        Map<String, Object> map = new HashMap<>();

        List<Device> devices = deviceService.getDeviceByCond(condition);
      /*  if (!"".equals(condition.getIccid())) {
            map.put("iccid", condition.getIccid());
        }
        if (!"".equals(condition.getPhone())) {

            map.put("phone", condition.getPhone());
        }
        if (!"".equals(condition.getSerial())) {

            map.put("serial", condition.getSerial());
        }
        if (!"".equals(condition.getRemark())) {

            map.put("remark", condition.getRemark());
        }
        if(condition.getOnline()!=null){
            map.put("online",condition.getOnline());
        }
        List<Device> devices = deviceDao.selectByMap(map);*/

      /*  Device device = new Device();
        BeanUtils.copyProperties(condition,device);*/
        return new Result(200, "请求成功", devices);
    }

    @DeleteMapping("/deleteDeviceById/{id}")
    public Result deleteDeviceById(@PathVariable Long id) {

        return new Result(200, "删除成功", deviceDao.deleteById(id));
    }

    @GetMapping("/getDeviceByGroupId/{id}")
    public Result getDeviceByGroupId(@PathVariable Long id) {

        List<Device> devices = deviceService.getDeviceByGroupId(id);

        return new Result(200,"查询成功",devices);
    }

    /**
     * 构建Select选择器中group
     * @return
     */
    @GetMapping("/getGroups")
    public Result getGroups() {
        //查询所有script
        List<Group> groupList = groupDao.selectList(null);
        List list = new ArrayList();
        //取出Id 和 name
        for (Group group : groupList) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", group.getId());
            map.put("label", group.getName());
            list.add(map);
        }

        return new Result(200, "查询成功", list);
    }

    /**
     * 构建统计数据
     * @return
     */
    @GetMapping("/getStatistics")
    public Result getStatistics() {
        StatisticsVo statisticsVo = new StatisticsVo();


        Integer total = deviceDao.selectCount(null);

        QueryWrapper<Device> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("online",1);
        Integer online = deviceDao.selectCount(queryWrapper);

        QueryWrapper<Device> query = new QueryWrapper<>();
        query.eq("online",0);
        Integer disOnline = deviceDao.selectCount(query);

        statisticsVo.setTotal(total);
        statisticsVo.setOnline(online);
        statisticsVo.setDisOnline(disOnline);

        return new Result(200, "查询成功", statisticsVo);
    }


    @GetMapping("/getNoGroup")
    public Result getNoGroup() {
        //查询所有未分组设备
        List<Device> list = deviceService.getNoGroup();
        return new Result(200, "查询成功", list);
    }
}
