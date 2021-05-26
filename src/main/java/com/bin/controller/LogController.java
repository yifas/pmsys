package com.bin.controller;

import com.bin.common.Result;
import com.bin.dao.LogDao;
import com.bin.pojo.Log;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RequestMapping("/device/log")
@RestController
public class LogController {

    @Resource
    private LogDao logDao;

    @PostMapping("/uploadLog")
    public Result uploadLog(@RequestBody Log log){
        log.setCreateTime(new Date());
        int insert = logDao.insert(log);
        if (insert>0){
            return new Result(200,"日志上传成功");
        }
        return new Result(401,"日志上传失败");
    }

}
