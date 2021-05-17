package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.GroupScript;

public interface GroupScriptDao extends BaseMapper<GroupScript> {

    Integer[] getGroupsByScriptId(Long id);

    void deleteByScriptId(Integer id);
}
