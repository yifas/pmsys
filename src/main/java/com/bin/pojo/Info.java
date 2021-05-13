package com.bin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 每台设备的信息信息
 */
@Data
public class Info {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * 手机串号
     */
    private String serial;

    /**
     * 创建时间 记录上线离线时间
     */
    private Date createTime;

    /**
     * 在线状态1在线0离线
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    public Info() {}
}
