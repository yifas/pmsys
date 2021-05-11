package com.bin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bin.dao.DeviceDao;
import com.bin.dao.GroupDao;
import com.bin.pojo.Device;
import com.bin.pojo.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class PmApplicationTests {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private GroupDao groupDao;


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

}
