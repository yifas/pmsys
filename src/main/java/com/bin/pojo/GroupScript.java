package com.bin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
@Data
public class GroupScript implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * id
     */
    private Integer id;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 脚本id
     */
    private Integer scriptId;

    public GroupScript() {}
}
