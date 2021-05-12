package com.bin.controller;

import com.bin.common.Result;
import com.bin.pojo.User;
import com.bin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device/user")
public class UserController {

    @Autowired
    private UserService userService;



    @PostMapping("/login")
    public Result login(@RequestBody User user){

        Result result = userService.login(user);

        return result;
    }

}
