package com.bin.dto;

import com.bin.pojo.Task;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryTaskDto extends Task implements Serializable {

    private Integer[] checkList;

}
