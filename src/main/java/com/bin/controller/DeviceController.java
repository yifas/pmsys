package com.bin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.DeviceDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryCondition;
import com.bin.pojo.Device;
import com.bin.service.DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device")
public class DeviceController {


    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceDao deviceDao;

    /**
     * 获取请求数据
     *
     * @param device
     * @return
     */
    @PostMapping("/addDevice")
    public Result addDevice(@RequestBody Device device) {
        int i = deviceService.addDevice(device);
        if (i < 1) {
            return new Result(300, "请求失败");
        }
        return new Result(200, "请求成功");
    }

    @PostMapping("/getDevice")
    public Result getDevice(@RequestBody PageQueryBean param) {
        Page<Device> page = new Page<>(param.getCurrentPage(), param.getPageSize());
        IPage<Device> list = deviceDao.selectPage(page, null);
        System.out.println(list);
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

        //todo 待优化
        if (!"".equals(condition.getIccid())) {
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
        List<Device> devices = deviceDao.selectByMap(map);

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

}
