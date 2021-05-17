package com.bin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Script implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * app名称
     */
    private String appName;

    /**
     * 设备选择
     */
    private Integer groupId;

    /**
     * 脚本地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 任务类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    public Script() {}
}
