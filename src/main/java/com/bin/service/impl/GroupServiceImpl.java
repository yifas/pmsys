package com.bin.service.impl;

import com.bin.dao.DeviceDao;
import com.bin.dao.GroupDao;
import com.bin.dao.GroupDeviceDao;
import com.bin.pojo.Group;
import com.bin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDao groupDao;


    @Autowired
    private GroupDeviceDao groupDeviceDao;

    @Override
    public void setGroup(HashMap map) {
        /*
            1.多对多 先遍历group
            2.为每个group分配device IDS

         */
        //取封装的数据
        List<Integer> groupIds = (List<Integer>) map.get("groupIds");
        List<Integer> deviceIds = (List<Integer>) map.get("deviceIds");

        for (Integer groupId : groupIds) {
            //更新关系表
            groupDeviceDao.insertIds(groupId,deviceIds);
            //关系表插入完成  更新group表中对应的device数量
            Group group = new Group();
            group.setId(groupId);
            group.setUpdateTime(new Date());
            group.setAmount(deviceIds.size());
            groupDao.updateById(group);
        }

    }
}
