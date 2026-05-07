package com.enterprise.ems.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 系统用户实体
 */
@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private Integer status; // 0-禁用 1-正常
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
