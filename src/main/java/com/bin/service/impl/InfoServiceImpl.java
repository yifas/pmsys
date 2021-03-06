package com.bin.service.impl;

import com.bin.dao.InfoDao;
import com.bin.dto.QueryInfoCondition;
import com.bin.pojo.Device;
import com.bin.pojo.Info;
import com.bin.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InfoServiceImpl implements InfoService {

    @Autowired
    private InfoDao infoDao;


    @Override
    public void insertInfo(List<Device> devices) {
        /*
            查询最近 已离线设备
         */
        //遍历离线的设备
        for (Device device : devices) {

            //查询最近的离线信息
            //已经是离线 不操作
            Integer status = infoDao.selectStatusBySerial(device.getSerial());
            //已经有离线记录
        /*    if (status == 0) {
                continue;
            }*/
            if (status != null && status == 0) {
                continue;
            }

            //status==1
            Info info = new Info();
            info.setSerial(device.getSerial());
            //此处离线时间应改为设备最新的updateTime
            //info.setCreateTime(new Date());
            info.setCreateTime(device.getUpdateTime());
            info.setStatus(0);
            infoDao.insert(info);


        }
    }

    @Override
    public List<Info> selectByMapCond(Map<String, Object> map) {

        return infoDao.selectByMapCond(map);
    }

    @Override
    public List<Info> getInfoByCond(QueryInfoCondition condition) {
        return infoDao.getInfoByCond(condition);
    }
}
