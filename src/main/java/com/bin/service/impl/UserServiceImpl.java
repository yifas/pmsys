package com.bin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bin.common.Result;
import com.bin.dao.UserDao;
import com.bin.dto.LoginDto;
import com.bin.pojo.User;
import com.bin.service.UserService;
import com.bin.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public Result login(User user) {


        if ("".equals(user.getUsername()) || "".equals(user.getPassword()) ){
            throw new RuntimeException("用户名或密码不能为空");
        }
        //通过登录名查询用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", user.getUsername());
        User uer=userDao.selectOne(wrapper);

        //比较密码
        if (uer!=null&&uer.getPassword().equals(user.getPassword())){
            LoginDto loginDto = new LoginDto();
            //这里token直接用一个uuid
            //使用jwt的情况下，会生成一个jwt token,jwt token里会包含用户的信息
            String token = JwtTokenUtil.createJWT(uer);

            loginDto.setToken(token);
            loginDto.setUserInfo(uer);
            log.info("用户信息: {},登录时间: {}",uer.getUsername(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return new Result(200,"",loginDto);
        }
        return new Result(401,"用户名或密码错误","");

    }
}
