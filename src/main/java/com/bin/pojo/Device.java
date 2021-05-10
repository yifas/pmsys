package com.bin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备信息表
 */
@Data
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * 手机iccid
     */
    private String iccid;
    /**
     * 手机串号
     */
    private String serial;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 设备名
     */
    private String deviceName;

    /**
     * 主机地址
     */
    private String hostIp;

    /**
     * 屏幕分辨率
     */
    private String screen;

    /**
     * 手机品牌
     */
    private String brand;

    /**
     * mac地址
     */
    private String macAdd;

    /**
     * 内存占比
     */
    private Integer memory;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 亮度
     */
    private Integer bright;

    /**
     * 电量
     */
    private Integer battery;

    /**
     * 充电状态1充电0未充电
     */
    private Integer charge;

    /**
     * 屏幕状态1亮屏0熄屏
     */
    private Integer screenOn;

    /**
     * 在线状态1在线0离线
     */
    private Integer online;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Device() {}
}
