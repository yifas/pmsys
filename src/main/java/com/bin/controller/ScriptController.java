package com.bin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.GroupDao;
import com.bin.dao.ScriptDao;
import com.bin.dto.PageQueryBean;
import com.bin.pojo.Group;
import com.bin.pojo.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/device/script")
public class ScriptController {


    @Autowired
    private ScriptDao scriptDao;

    @PostMapping("/getScript")
    public Result getScript(@RequestBody PageQueryBean param) {
        Page<Script> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        IPage<Script> list = scriptDao.selectPage(page,null);

        return new Result(200, "请求成功", list);
    }


}
