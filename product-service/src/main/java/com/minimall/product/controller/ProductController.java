package com.minimall.product.controller;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.minimall.common.Result;
import com.minimall.product.entity.Product;
import com.minimall.product.entity.Stock;
import com.minimall.product.mapper.ProductMapper;
import com.minimall.product.mapper.StockMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Resource
    private ProductMapper productMapper;
    @Resource
    private StockMapper stockMapper;

    @GetMapping("/{id}")
    public Result<Product> getProduct(@PathVariable Long id) {
        return Result.success(productMapper.selectById(id));
    }

    // 扣减库存（内部调用接口）
    @PostMapping("/{id}/deductStock")
    public Result<Boolean> deductStock(@PathVariable Long id, @RequestParam Integer quantity) {
        // 使用 MyBatis-Plus 的 update 方法，利用 version 乐观锁防止超卖
        int rows = stockMapper.update(null, new LambdaUpdateWrapper<Stock>()
                .eq(Stock::getProductId, id)
                .ge(Stock::getQuantity, quantity) // 库存必须 >= 扣减数量
                .setSql("quantity = quantity - {0}", quantity)
                .setSql("version = version + 1"));
        return rows > 0 ? Result.success(true) : Result.error("库存不足或扣减失败");
    }
}