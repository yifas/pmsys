package com.bin.dto;

import com.bin.pojo.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于数据传输
 */
@Data
public class LoginDto implements Serializable {

    private String token;
    private User userInfo;

}
