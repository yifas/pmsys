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
        wrapper.eq("serial",device.getSerial());
        Device one = deviceDao.selectOne(wrapper);
        //如果串号不存在
        if (one==null){
            device.setCreateTime(new Date());
            device.setUpdateTime(new Date());
            //刚刚创建 并且没有再次访问该接口 显示为0分钟
            device.setLastOnlineTime(0);
            //第一次创建的时候统一设置为0  【如果长时间没有第二次访问设置为1 就不合理】
            device.setOnline(0);
            return deviceDao.insert(device);
        }

        //获取数据库中updateTime
        if (one.getUpdateTime()==null){
            return 0;
        }
        //如果串号 更新时间 存在  就更新这条数据
        UpdateWrapper<Device> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("serial",device.getSerial());

        //时间做差
        Date updateTime = one.getUpdateTime();
        //数据库更新时间 时间戳
        long updateTimes = updateTime.getTime();
        //转成秒
        long minute = System.currentTimeMillis()/1000-updateTimes/1000;
        if (minute <= 60){
            //判断当前设备是否有最近的下线记录 (IMEI status=0 where createTime最近)
            QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("status",0);
            queryWrapper.eq("serial",device.getSerial());
            //按时间降序
            queryWrapper.orderByDesc("createTime");
            //最近下线记录    ----------可能存在多条
            Info info = infoDao.selectOne(queryWrapper);
            //有下线记录
            if(info!=null){
                //需要在【Info】添加上线记录
                Info newInfo = new Info();
                newInfo.setSerial(device.getSerial());
                newInfo.setCreateTime(new Date());
                newInfo.setStatus(1);
                infoDao.insert(newInfo);
            }
            //都要更新device
            //设置在线
            device.setOnline(1);
            device.setUpdateTime(new Date());
            deviceDao.update(device,updateWrapper);
        }else{ // >60s 要请求了才能 比较  如果不请求怎么判断  需要新的任务去判断 （请求来了再去判断 ，有请求表明设备必定已经在线 再更新到离线状态 逻辑错误）
            // 能进到该分支 手机必定在线    那么device表就不会有【离线】状态了
            /*
                1.没有这个else分支
                2.需要另起定时任务 循环遍历 数据库表中设备的updateTime和当前时间比较 （不是好的方法）
                3.如果遍历到》60s的 就添加下线数据到【Info】
             */
        }

      /*  //获取数据库中updateTime
        if (one.getUpdateTime()!=null){
            //时间做差 转换分钟
            Date updateTime = one.getUpdateTime();
            //数据库更新时间 时间戳
            long updateTimes = updateTime.getTime();
            //转成分钟
            long minute = System.currentTimeMillis()/1000/60-updateTimes/1000/60;
            device.setLastOnlineTime((int) minute);
            //判断是否在线逻辑 约定30分钟之内访问该接口为在线
            if (minute<1){
                device.setOnline(1);
            }else {
                device.setOnline(0);
            }
        }else {
            device.setUpdateTime(new Date());
            //如果是第一次update 显示为0分钟
            device.setLastOnlineTime(0);
        }*/

        return deviceDao.update(device,updateWrapper);

    }

    @Override
    public List<Device> getDeviceByGroupId(Long id) {

        return deviceDao.getDeviceByGroupId(id);
    }
}
