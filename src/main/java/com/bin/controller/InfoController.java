package com.bin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bin.common.Result;
import com.bin.dao.InfoDao;
import com.bin.dto.PageQueryBean;
import com.bin.dto.QueryInfoCondition;
import com.bin.pojo.Info;
import com.bin.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/device/info")
public class InfoController {

    @Autowired
    private InfoDao infoDao;

    @Autowired
    private InfoService infoService;


    @PostMapping("/getInfo")
    public Result getInfo(@RequestBody PageQueryBean param) {
        Page<Info> page = new Page<>(param.getCurrentPage(), param.getPageSize());

        IPage<Info> list = infoDao.selectPage(page,null);

        return new Result(200, "请求成功", list);
    }


    @PostMapping("/getInfoByCond")
    public Result getInfoByCond(@RequestBody QueryInfoCondition condition) {
        //多条件查询
        Map<String, Object> map = new HashMap<>();

        //todo 待优化
        if (!"".equals(condition.getId())) {
            map.put("serial", condition.getId());
        }
        if (!"".equals(condition.getValue())) {

            map.put("status", condition.getValue());
        }
        if (!"".equals(condition.getShowDate()) && condition.getShowDate()!=null ) {

            map.put("createTime", condition.getShowDate());
        }
        if (!"".equals(condition.getRemark())) {

            map.put("remark", condition.getRemark());
        }
        //List<Info> groups = infoDao.selectByMap(map);

        List<Info> groups = infoService.selectByMapCond(map);

      /*  Device device = new Device();
        BeanUtils.copyProperties(condition,device);*/
        return new Result(200, "查询成功", groups);
    }
}
