package com.bin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bin.dto.QueryInfoCondition;
import com.bin.pojo.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InfoDao extends BaseMapper<Info> {
    /**
     * 查询设备下线记录
     * @param serial
     * @return
     */
    Info selectBySerial(@Param("serial") String serial);

    /**
     * 查询状态
     * @param serial
     * @return
     */
    Integer selectStatusBySerial(@Param("serial") String serial);

    /**
     *
     * @param map
     * @return
     */
    List<Info> selectByMapCond(Map<String, Object> map);

    /**
     * 多条件分组查询
     * @param condition
     * @return
     */
    List<Info> getInfoByCond(QueryInfoCondition condition);
}
