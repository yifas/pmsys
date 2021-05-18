package com.bin.service;

import com.bin.pojo.Task;

import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Task> selectByMapCond(Map<String, Object> map);
}
