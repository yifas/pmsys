package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bin.dao.DeviceDao;
import com.bin.pojo.Device;
import com.bin.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;


    /*
        1. 如果串号不存在 存储信息到数据库
        2. 如果串号存在 更新信息 并且修改更新时间
        - 在线状态的判断 当前时间和最新的修改时间比较  （写在数据展示接口）
        - 上次在线时间 也是当前时间和最新的修改时间比较（写在数据展示接口）
     */
    @Override
    public int addDevice(Device device) {
        //判断串号是否已经存在
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("serial",device.getSerial());
        Device one = deviceDao.selectOne(wrapper);
        //如果串号不存在
        if (one==null){
            device.setCreateTime(new Date());
            return deviceDao.insert(device);
        }
        //如果串号存在  就更新这条数据
        UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("serial",device.getSerial());
        device.setUpdateTime(new Date());
        return deviceDao.update(device,updateWrapper);

    }
}