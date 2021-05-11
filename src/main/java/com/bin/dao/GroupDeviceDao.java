package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.GroupDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupDeviceDao extends BaseMapper<GroupDevice> {
    /**
     * 批量插入数据
     * @param groupId
     * @param deviceIds
     */
    void insertIds(@Param("groupId") Integer groupId,List<Integer> deviceIds);
}
