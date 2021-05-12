package com.bin.service;

import com.bin.common.Result;
import com.bin.dto.LoginDto;
import com.bin.pojo.User;

public interface UserService {

    /**
     * 用户登录 并返回Token
     * @param user
     * @return
     */
    Result login(User user);
}
