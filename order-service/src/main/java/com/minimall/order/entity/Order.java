package com.minimall.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order")
public class Order {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long productId;    // 商品ID（为了简化，直接存主表）
    private Integer quantity;  // 购买数量
    private String orderNo;    // 订单编号
    private BigDecimal totalAmount;
    private Integer status;    // 0-待支付，1-已支付，2-已取消
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}