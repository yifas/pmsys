package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceDao extends BaseMapper<Device> {

    List<Device> getDeviceByGroupId(@Param("id") Long id);

    void updateByCurrentTime();

    List<Device> selectIds();
}
