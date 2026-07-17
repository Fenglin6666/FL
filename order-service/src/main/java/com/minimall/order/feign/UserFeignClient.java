package com.minimall.order.feign;

import com.minimall.common.Result;
import com.minimall.order.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserFeignClient {
    @GetMapping("/users/{id}")
    Result<UserDTO> getUser(@PathVariable("id") Long id);
}

