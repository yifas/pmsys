package com.bin.service;

public interface GroupScriptService {
    /**
     * 根据脚本ID 返回对应的分组
     * @param id
     * @return
     */
    Integer[] getGroupsByScriptId(Long id);

    /**
     * 更新关系表
     * @param id
     * @param checkList
     */
    void updateRelation(Integer id, Integer[] checkList);
}
