package com.bin.service;

import com.bin.common.Result;
import com.bin.dto.QueryCondition;
import com.bin.pojo.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceService {
    /**
     * 获取请求中的数据
     * @param device
     * @return
     */
    Result addDevice(Device device);

    /**
     * 通过分组查询设备
     * @param id
     * @return
     */
    List<Device> getDeviceByGroupId(Long id);

    /**
     * 判断时间差 更新状态
     * @return
     */
    int updateByCurrentTime();

    /**
     * 获取时间差>60 的ID
     * @return
     */
    List<Device> selectIds();

    /**
     * 多条件查询
     * @param condition
     * @return
     */
    List<Device> getDeviceByCond(QueryCondition condition);

    /**
     * 查询所有未分组设备
     * @return
     */
    List<Device> getNoGroup();

}
