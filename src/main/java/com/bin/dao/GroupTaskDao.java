package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.GroupTask;

public interface GroupTaskDao extends BaseMapper<GroupTask> {


    Integer[] getGroupIds(Long id);

}
