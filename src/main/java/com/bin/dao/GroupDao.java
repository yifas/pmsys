package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.pojo.Device;
import com.bin.pojo.Group;

import java.util.List;

public interface GroupDao extends BaseMapper<Group> {

    Integer listDevice(Integer id);
}
