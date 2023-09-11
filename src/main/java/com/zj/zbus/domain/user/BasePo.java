package com.zj.zbus.domain.user;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BasePo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 创建人和更新人信息
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 是否删除 (0 未删除 1 已删除)
     */
    private Boolean isDeleted = false;
}
