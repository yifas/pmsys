package com.bin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Log implements Serializable {

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
     * 脚本名字
     */
    private String jsName;

    /**
     * 日志内容
     */
    private String txt;

    /**
     * 日志级别
     */
    private String level;

    /**
     * 创建时间
     */
    private Date createTime;

    public Log() {}
}
