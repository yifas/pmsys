package com.bin.service.impl;

import com.bin.dao.GroupDao;
import com.bin.dao.GroupDeviceDao;
import com.bin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

            groupDeviceDao.insertIds(groupId,deviceIds);
        }

    }
}
