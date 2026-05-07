package com.enterprise.ems.controller;

import com.enterprise.ems.common.Result;
import com.enterprise.ems.dto.LoginRequest;
import com.enterprise.ems.dto.LoginResponse;
import com.enterprise.ems.dto.RegisterRequest;
import com.enterprise.ems.entity.User;
import com.enterprise.ems.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 认证控制器
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("[API] POST /api/auth/login - username: {}", request.getUsername());
        LoginResponse response = authService.login(request);
        return Result.success("登录成功", response);
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("[API] POST /api/auth/register - username: {}", request.getUsername());
        authService.register(request);
        return Result.success("注册成功", null);
    }

    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public Result<User> getUserInfo(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        logger.info("[API] GET /api/auth/info - userId: {}", userId);
        User user = authService.getUserInfo(userId);
        return Result.success(user);
    }
}
