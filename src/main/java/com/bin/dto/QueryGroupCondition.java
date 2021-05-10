package com.bin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询参数
 */
@Data
public class QueryGroupCondition implements Serializable {

    private Long id;
    private String name;
    private String remark;
}
