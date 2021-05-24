package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.bin.common.Result;
import com.bin.dao.DeviceDao;
import com.bin.dao.DictDao;
import com.bin.dao.InfoDao;
import com.bin.dao.ScriptDao;
import com.bin.dto.QueryCondition;
import com.bin.pojo.*;
import com.bin.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private InfoDao infoDao;

    @Autowired
    private ScriptDao scriptDao;

    @Autowired
    private DictDao dictDao;

    @Override
    public Result addDevice(Device device) {
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
            //通过ICCID查询手机号 并设置
            if (device.getIccid() != null) {
                QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("iccid", device.getIccid());
                Dict dict = dictDao.selectOne(queryWrapper);
                if (dict != null) {
                    device.setPhone(dict.getPhone());
                }
            }
            log.info("设备串号: {},新增时间: {}", device.getSerial(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return new Result(200, "新增设备成功", deviceDao.insert(device));
        }
        //获取数据库中updateTime 【是否必要】
        /*if (one.getUpdateTime() == null) {
            return new Result(401,"新增设备失败");
        }*/
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

            if(device.getPhone()==null){
                //通过ICCID查询手机号 并设置
                if (device.getIccid() != null) {
                    QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("iccid", device.getIccid());
                    Dict dict = dictDao.selectOne(queryWrapper);
                    if (dict != null) {
                        device.setPhone(dict.getPhone());
                    }
                }
            }

            deviceDao.update(device, updateWrapper);
            //当前时间 设备串号
            /*
                1.查询的是计划任务表 配置时间应该在计划任务表完成 计划任务表配置了应该执行什么脚本 在什么时间段完成
                2.需要根据当前时间和设备串号查询计划任务表
                3.如果存在 返回 脚本名字/id type 任务类型 0停止所有 1 正常执行 不存在返回null
                4.新增定时任务时 判断时间段冲突 是针对每个手机=》对应执行任务的时间段
             */
            //1.查询该设备 当前时间段的任务
            //1.1 查询serial 所在组
            //1.2 查询改组在当前时间段中的task信息【需要task表时间段不重复】
            Task task = deviceDao.selectTaskBySerial(device.getSerial());
            HashMap map = new HashMap();
            //先查到其对应的task
            if (task != null) {
                //脚本名称
                //task.getScriptName();
                //查询脚本信息
                QueryWrapper<Script> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("name", task.getScriptName());
                Script script = scriptDao.selectOne(queryWrapper);
                if (script.getName() != null && !"".equals(script.getName())) {
                    map.put("scriptName", script.getName());
                }
                if (script.getAppName() != null && !"".equals(script.getAppName())) {
                    map.put("appName", script.getAppName());
                }
                if (script.getAddress() != null && !"".equals(script.getAddress())) {
                    map.put("address", script.getAddress());
                }
                //计划任务ID
                if (task.getId() != null) {
                    map.put("taskId", task.getId());
                }

            }

            return new Result(200, "更新设备&查询当前任务成功", map);
        }
        // >60s的情况
        //判断当前设备是否有最近的下线记录 (IMEI status=0 where createTime最近)
        //最近下线记录    ----------可能存在多条,已经改成最近的一条
        Info info = infoDao.selectBySerial(device.getSerial());
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
        return new Result(200, "更新成功", deviceDao.update(device, updateWrapper));

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

    @Override
    public List<Device> getDeviceByCond(QueryCondition condition) {
        return deviceDao.getDeviceByCond(condition);
    }

    @Override
    public List<Device> getNoGroup() {
        return deviceDao.getNoGroup();
    }
}
