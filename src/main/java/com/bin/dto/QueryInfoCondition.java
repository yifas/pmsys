package com.bin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 */
@Data
public class QueryInfoCondition implements Serializable {

    private String id;
    private String value;
    private String remark;
    private String showDate;
}
