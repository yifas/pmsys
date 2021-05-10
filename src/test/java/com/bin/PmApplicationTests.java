package com.bin;

import com.bin.dao.DeviceDao;
import com.bin.dao.GroupDao;
import com.bin.pojo.Device;
import com.bin.pojo.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


}
