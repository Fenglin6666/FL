package com.minimall.order.feign;

import com.minimall.common.Result;
import com.minimall.order.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-service")
public interface ProductFeignClient {
    @GetMapping("/products/{id}")
    Result<ProductDTO> getProduct(@PathVariable("id") Long id);

    @PostMapping("/products/{id}/deductStock")
    Result<Boolean> deductStock(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);
}
