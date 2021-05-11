package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bin.dao.DeviceDao;
import com.bin.pojo.Device;
import com.bin.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

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
            //刚刚创建 并且没有再次访问该接口 显示为0分钟
            device.setLastOnlineTime(0);
            //第一次创建的时候统一设置为0  【如果长时间没有第二次访问设置为1 就不合理】
            device.setOnline(0);
            return deviceDao.insert(device);
        }
        //如果串号存在  就更新这条数据
        UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("serial",device.getSerial());

        //获取数据库中updateTime
        if (one.getUpdateTime()!=null){
            //时间做差 转换分钟
            Date updateTime = one.getUpdateTime();
            //数据库更新时间 时间戳
            long updateTimes = updateTime.getTime();
            //转成分钟
            long minute = System.currentTimeMillis()/1000/60-updateTimes/1000/60;
            device.setLastOnlineTime((int) minute);
            //判断是否在线逻辑 约定30分钟之内访问该接口为在线
            if (minute<30){
                device.setOnline(1);
            }else {
                device.setOnline(0);
            }
        }else {
            device.setUpdateTime(new Date());
            //如果是第一次update 显示为0分钟
            device.setLastOnlineTime(0);
        }
        return deviceDao.update(device,updateWrapper);

    }

    @Override
    public List<Device> getDeviceByGroupId(Long id) {

        return deviceDao.getDeviceByGroupId(id);
    }
}
