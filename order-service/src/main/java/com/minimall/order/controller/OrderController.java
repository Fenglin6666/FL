package com.minimall.order.controller;

import com.minimall.common.Result;
import com.minimall.order.dto.ProductDTO;
import com.minimall.order.dto.UserDTO;
import com.minimall.order.entity.Order;
import com.minimall.order.feign.ProductFeignClient;
import com.minimall.order.feign.UserFeignClient;
import com.minimall.order.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private OrderMapper  orderMapper;

    @PostMapping
    public Result<Order> createOrder(@RequestBody Order orderRequest) {
        // 1. 校验用户是否存在
        Long userId = orderRequest.getUserId();
        Result<UserDTO> userResult = userFeignClient.getUser(userId);
        if (userResult.getCode() != 200 || userResult.getData() == null) {
            return Result.error("用户不存在");
        }

        // 2. 调用商品服务扣减库存
        Long productId = orderRequest.getProductId();
        Integer quantity = orderRequest.getQuantity();
        Result<Boolean> stockResult = productFeignClient.deductStock(productId, quantity);
        if (stockResult.getCode() != 200 || !stockResult.getData()) {
            return Result.error("库存不足，下单失败");
        }

        // 3. 查询商品详情获取价格
        Result<ProductDTO> productResult = productFeignClient.getProduct(productId);
        BigDecimal price = productResult.getData().getPrice();
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

        // 4. 保存订单到本地数据库
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setTotalAmount(totalAmount);
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        order.setStatus(0); // 待支付

        orderMapper.insert(order);
        return Result.success(order);
    }
}