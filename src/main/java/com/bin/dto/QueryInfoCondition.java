package com.bin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 */
@Data
public class QueryInfoCondition implements Serializable {

    private String serial;
    private Integer status;
    private String remark;
    private String createTime;
    //分页
    private Integer page;
    private Integer size;

}
