package com.enterprise.ems.service;

import com.enterprise.ems.dto.LoginRequest;
import com.enterprise.ems.dto.LoginResponse;
import com.enterprise.ems.dto.RegisterRequest;
import com.enterprise.ems.entity.User;
import com.enterprise.ems.exception.BusinessException;
import com.enterprise.ems.mapper.UserMapper;
import com.enterprise.ems.util.JwtUtil;
import com.enterprise.ems.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 认证服务
 */
@Service
public class AuthService {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordUtil passwordUtil;

    /**
     * 用户登录
     */
    public LoginResponse login(LoginRequest request) {
        logger.info("[登录] 用户尝试登录: {}", request.getUsername());
        
        User user = userMapper.findByUsername(request.getUsername());
        if (user == null) {
            logger.warn("[登录失败] 用户不存在: {}", request.getUsername());
            throw new BusinessException("用户名或密码错误");
        }
        
        if (!passwordUtil.matches(request.getPassword(), user.getPassword())) {
            logger.warn("[登录失败] 密码错误: {}", request.getUsername());
            throw new BusinessException("用户名或密码错误");
        }
        
        if (user.getStatus() != 1) {
            logger.warn("[登录失败] 账号已禁用: {}", request.getUsername());
            throw new BusinessException("账号已被禁用");
        }
        
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        logger.info("[登录成功] 用户: {}", request.getUsername());
        
        return new LoginResponse(token, user.getId(), user.getUsername(), 
                user.getNickname(), user.getAvatar());
    }

    /**
     * 用户注册
     */
    public void register(RegisterRequest request) {
        logger.info("[注册] 用户尝试注册: {}", request.getUsername());
        
        if (userMapper.countByUsername(request.getUsername()) > 0) {
            logger.warn("[注册失败] 用户名已存在: {}", request.getUsername());
            throw new BusinessException("用户名已存在");
        }
        
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordUtil.encode(request.getPassword()));
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setStatus(1);
        
        userMapper.insert(user);
        logger.info("[注册成功] 用户: {}", request.getUsername());
    }

    /**
     * 获取当前用户信息
     */
    public User getUserInfo(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setPassword(null); // 不返回密码
        return user;
    }
}
