package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bin.dao.GroupScriptDao;
import com.bin.pojo.GroupScript;
import com.bin.service.GroupScriptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupScriptServiceImpl implements GroupScriptService {

    @Autowired
    private GroupScriptDao groupScriptDao;


    @Override
    public Integer[] getGroupsByScriptId(Long id) {
        Integer[] groupsByScriptId = groupScriptDao.getGroupsByScriptId(id);
        return groupsByScriptId;
    }

    @Override
    public void updateRelation(Integer id, Integer[] checkList) {

        //删除原有关系
        groupScriptDao.deleteByScriptId(id);
        //建立新的关系
        for (Integer i : checkList) {
            GroupScript scriptDao = new GroupScript();
            scriptDao.setScriptId(id);
            scriptDao.setGroupId(i);
            groupScriptDao.insert(scriptDao);
        }

    }
}
