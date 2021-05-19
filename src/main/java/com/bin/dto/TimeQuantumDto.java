package com.bin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TimeQuantumDto implements Serializable {

    private Date createTime;
    private Date endTime;
}
