package com.bin;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bin.dao.DeviceDao;
import com.bin.dao.GroupDao;
import com.bin.dao.InfoDao;
import com.bin.dto.QueryCondition;
import com.bin.dto.TimeQuantumDto;
import com.bin.pojo.Device;
import com.bin.pojo.Group;
import com.bin.pojo.Info;
import com.bin.pojo.Task;
import com.bin.service.DeviceService;
import com.bin.service.TaskService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class PmApplicationTests {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private GroupDao groupDao;

    @Autowired
    private InfoDao infoDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private DeviceService deviceService;

    @Test
    void contextLoads() {
        List<Device> devices = deviceDao.selectList(null);
        devices.forEach(System.out::println);
    }


    @Test
    void testGroup() {
        List<Group> devices = groupDao.selectList(null);
        devices.forEach(System.out::println);
    }

    @Test
    void testTime(){

        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("id",3);
        Device one = deviceDao.selectOne(wrapper);

        //获取数据库中updateTime
        if (one.getUpdateTime()!=null){
            //时间做差 转换分钟
            Date updateTime = one.getUpdateTime();
            //数据库更新时间 时间戳
            long updateTimes = updateTime.getTime();

            System.out.println(updateTimes);

            System.out.println(System.currentTimeMillis());

            System.err.println(System.currentTimeMillis()/1000/60-updateTimes/1000/60);
        }

    }

    @Test
    void testUpdate(){
        //测试未传值的是否更新
        Group group = new Group();
        group.setId(6);
        group.setName("bb");
        group.setAmount(10);
        groupDao.updateById(group);

    }

    @Test
    void testSelectOne(){
        QueryWrapper<Info> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 0);
        queryWrapper.eq("serial", "ccccca445a59524853");
        //按时间降序
        queryWrapper.orderByDesc("createTime");
        queryWrapper.last("limit0,1");
        //最近下线记录    ----------可能存在多条
        Info info = infoDao.selectOne(queryWrapper);
        System.err.println(info);
    }

    @Test
    void testSelectTaskBySerial(){
        Task task = deviceDao.selectTaskBySerial("22221a445a59524853");
        System.out.println(task);
    }

    @Test
    void testGetTimeByCodi(){
        List<TimeQuantumDto> list = taskService.getTimeByCodi("头条");
        System.out.println(list);
    }

    @Test
    void testPlusTime(){
        Date date = new Date();
        System.out.println(date);
        System.out.println(date.getTime()+600000);
    }

    @Test
    void testSelectPage(){
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.setOnline(0);
        queryCondition.setPage(1);
        queryCondition.setSize(2);

        List<Device> devices = deviceService.getDeviceByCond(queryCondition);
        List list = new ArrayList();


        for (Device device : devices) {
            String s = JSON.toJSONString(device);
            list.add(s);
        }
        System.out.println(list);
    }



}



