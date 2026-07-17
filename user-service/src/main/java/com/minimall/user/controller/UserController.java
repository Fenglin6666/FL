package com.minimall.user.controller;
import com.minimall.common.Result;
import com.minimall.user.entity.User;
import com.minimall.user.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    private UserMapper userMapper;

    // 注册（保存用户）
    @PostMapping
    public Result<User> register(@RequestBody User user) {
        userMapper.insert(user);
        return Result.success(user);
    }

    // 根据ID查询（供订单服务Feign调用）
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        User user = userMapper.selectById(id);
        return Result.success(user);
    }
}