package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bin.dao.DeviceDao;
import com.bin.dao.GroupDao;
import com.bin.dao.GroupDeviceDao;
import com.bin.pojo.Device;
import com.bin.pojo.Group;
import com.bin.pojo.GroupDevice;
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
            //判断关系表中groupId是否已经存在
            QueryWrapper<GroupDevice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("groupId",groupId);
            List<GroupDevice> list = groupDeviceDao.selectList(queryWrapper);
            //groupId存在
            if (list!=null&&list.size()>0){
                //遍历判断deviceIds在关系表中是否存在
                for (Integer deviceId : deviceIds) {
                    //查询当前groupId下是否存在该deviceId 不存在则插入
                    QueryWrapper<GroupDevice> queryWrapper1 = new QueryWrapper<>();
                    queryWrapper1.eq("groupId",groupId);
                    queryWrapper1.eq("deviceId",deviceId);
                    GroupDevice device = groupDeviceDao.selectOne(queryWrapper1);
                    if (device==null){
                        GroupDevice groupDevice1 = new GroupDevice();
                        groupDevice1.setGroupId(groupId);
                        groupDevice1.setDeviceId(deviceId);
                        groupDeviceDao.insert(groupDevice1);
                    }
                }

            }else {
                //直接更新关系表
                groupDeviceDao.insertIds(groupId,deviceIds);
            }
            //关系表插入完成  更新group表中对应的device数量
            Group group = new Group();
            group.setId(groupId);
            group.setUpdateTime(new Date());
            group.setAmount(deviceIds.size());
            groupDao.updateById(group);
        }

    }

    @Override
    public Integer listDevice(Integer id) {
        return groupDao.listDevice(id);
    }
}
