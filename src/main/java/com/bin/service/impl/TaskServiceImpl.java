package com.bin.service.impl;

import com.bin.dao.TaskDao;
import com.bin.pojo.Task;
import com.bin.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {


    @Autowired
    private TaskDao taskDao;

    @Override
    public List<Task> selectByMapCond(Map<String, Object> map) {
        return taskDao.selectByMapCond(map);
    }
}
