package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.Device;
import com.bin.pojo.Script;
import com.bin.pojo.Task;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceDao extends BaseMapper<Device> {

    List<Device> getDeviceByGroupId(@Param("id") Long id);

    void updateByCurrentTime();

    List<Device> selectIds();

    Task selectTaskBySerial(String serial);
}
