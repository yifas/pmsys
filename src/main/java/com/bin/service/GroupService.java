package com.bin.service;


import java.util.HashMap;

public interface GroupService {

    /**
     * 设备设置分组
     * @param map
     */
    void setGroup(HashMap map);

    /**
     * 根据分组查询设备
     * @param id
     * @return
     */
    Integer listDevice(Integer id);
}
