package com.bin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 */
@Data
public class QueryCondition implements Serializable {

    private String iccid;
    private String phone;
    private String serial;
    private String remark;
    private Integer online;
    private Integer group;
}
