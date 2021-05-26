package com.bin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * 手机串号
     */
    private String meid;

    /**
     * 任务状态
     */
    private Integer status;

    /**
     * 脚本名字
     */
    private String jsName;

    /**
     * 任务状态描述
     */
    private String msg;

    /**
     * 创建时间
     */
    private Date createTime;

    public Progress() {}
}
