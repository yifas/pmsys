package com.bin.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class Task implements Serializable {
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
     * 任务名称
     */
    private String scriptName;

    /**
     * 设备分组 展示
     */
    private String groupName;

    /**
     * 执行时间
     */
    private Integer duration;

    /**
     * 任务类型
     */
    private Integer type;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 执行结果
     */
    private String result;

    /**
     * 备注
     */
    private String remark;

    /**
     * 开始时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间 可能用于获取状态
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 结束时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    public Task() {}
}
