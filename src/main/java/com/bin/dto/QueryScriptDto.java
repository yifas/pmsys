package com.bin.dto;

import com.bin.pojo.Script;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryScriptDto extends Script implements Serializable {

    private Integer[] checkList;

}
