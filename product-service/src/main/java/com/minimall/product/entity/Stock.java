package com.minimall.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("stock")
public class Stock {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long productId;
    private Integer quantity;
    private Integer version; // 乐观锁版本号
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}