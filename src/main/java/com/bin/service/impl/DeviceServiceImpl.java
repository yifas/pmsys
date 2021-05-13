package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bin.dao.DeviceDao;
import com.bin.dao.InfoDao;
import com.bin.pojo.Device;
import com.bin.pojo.Info;
import com.bin.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private InfoDao infoDao;

    @Override
    public int addDevice(Device device) {
        //判断串号是否已经存在
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("serial", device.getSerial());
        Device one = deviceDao.selectOne(wrapper);
        //如果串号不存在
        if (one == null) {
            device.setCreateTime(new Date());
            device.setUpdateTime(new Date());
            //刚刚创建 并且没有再次访问该接口 显示为0分钟
            device.setLastOnlineTime(0);
            //第一次创建的时候统一设置为0  【如果长时间没有第二次访问设置为1 就不合理】
            device.setOnline(0);
            return deviceDao.insert(device);
        }
        //获取数据库中updateTime 【是否必要】
        if (one.getUpdateTime() == null) {
            return 0;
        }
        //如果串号 更新时间 存在  就更新这条数据
        UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("serial", device.getSerial());

        //时间做差
        Date updateTime = one.getUpdateTime();
        //数据库更新时间 时间戳
        long updateTimes = updateTime.getTime();
        //转成秒
        long minute = System.currentTimeMillis() / 1000 - updateTimes / 1000;
        if (minute <= 60) {
            //都要更新device
            //设置在线
            device.setOnline(1);
            device.setUpdateTime(new Date());
            return deviceDao.update(device, updateWrapper);
        }
        // >60s的情况
        //判断当前设备是否有最近的下线记录 (IMEI status=0 where createTime最近)
        //最近下线记录    ----------可能存在多条
        Info info =infoDao.selectBySerial(device.getSerial());
        //有下线记录
        if (info != null) {
            //需要在【Info】添加上线记录
            Info newInfo = new Info();
            newInfo.setSerial(device.getSerial());
            newInfo.setCreateTime(new Date());
            newInfo.setStatus(1);
            infoDao.insert(newInfo);
        }
        //device表也要更新数据
        //设置在线
        device.setOnline(1);
        device.setUpdateTime(new Date());
        return deviceDao.update(device, updateWrapper);

    }

    @Override
    public List<Device> getDeviceByGroupId(Long id) {

        return deviceDao.getDeviceByGroupId(id);
    }

    @Override
    public int updateByCurrentTime() {
        try {
            deviceDao.updateByCurrentTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

    @Override
    public List<Device> selectIds() {
        return deviceDao.selectIds();

    }
}
