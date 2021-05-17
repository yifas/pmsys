package com.bin.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    private Integer groupId;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间 可能用于获取状态
     */
    private Date updateTime;

    /**
     * 结束时间
     */
    private Date endTime;

    public Task() {}
}
