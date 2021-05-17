package com.bin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueryScriptCondition implements Serializable {

    private String name;
    private String appName;
    private String remark;
}
