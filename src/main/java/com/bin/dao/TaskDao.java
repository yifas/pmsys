package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.Task;

import java.util.List;
import java.util.Map;

public interface TaskDao extends BaseMapper<Task> {

    List<Task> selectByMapCond(Map<String, Object> map);
}
