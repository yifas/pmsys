package com.bin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryTaskCondition implements Serializable {

    private String scriptName;
    private String groupName;
    private String createTime;
    private String remark;
    private Integer status;
}
