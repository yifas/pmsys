package com.bin.service;

import com.bin.dto.TimeQuantumDto;
import com.bin.pojo.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Task> selectByMapCond(Map<String, Object> map);

    /**
     * 根据分组 查询已有的时间段
     * @param name
     * @return
     */
    List<TimeQuantumDto> getTimeByCodi(String name);
}
