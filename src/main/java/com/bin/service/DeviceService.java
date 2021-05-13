package com.bin.service;

import com.bin.pojo.Device;

import java.util.List;

public interface DeviceService {
    /**
     * 获取请求中的数据
     * @param device
     * @return
     */
    int addDevice(Device device);

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

}
