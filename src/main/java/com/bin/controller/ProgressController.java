package com.bin.controller;

import com.bin.common.Result;
import com.bin.dao.ProgressDao;
import com.bin.pojo.Progress;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RequestMapping("/device/progress")
@RestController
public class ProgressController {

    @Resource
    private ProgressDao progressDao;

    @PostMapping("/addProgress")
    public Result addProgress(@RequestBody Progress progress){

        progress.setCreateTime(new Date());
        int insert = progressDao.insert(progress);
        if (insert>0){
            return new Result(200,"新增任务进度成功");
        }
        return new Result(401,"新增任务进度失败");
    }



}
