package com.bin.service.impl;

import com.bin.dao.GroupTaskDao;
import com.bin.service.GroupTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupTaskServiceImpl implements GroupTaskService {

    @Autowired
    private GroupTaskDao groupTaskDao;



    @Override
    public Integer[] getGroupIds(Long id) {
        return groupTaskDao.getGroupIds(id);
    }
}
