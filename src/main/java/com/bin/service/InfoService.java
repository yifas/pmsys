package com.bin.service;

import com.bin.dto.QueryInfoCondition;
import com.bin.pojo.Device;
import com.bin.pojo.Info;

import java.util.List;
import java.util.Map;

public interface InfoService {
    /**
     * 插入下线记录
     * @param devices
     */
    void insertInfo(List<Device> devices);

    /**
     * 多条件查询
     * @param map
     * @return
     */
    List<Info> selectByMapCond(Map<String, Object> map);

    /**
     * 多条件分页查询
     * @param condition
     * @return
     */
    List<Info> getInfoByCond(QueryInfoCondition condition);
}
