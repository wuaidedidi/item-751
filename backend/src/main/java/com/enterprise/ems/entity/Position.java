package com.enterprise.ems.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 职位实体
 */
@Data
public class Position {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer sortOrder;
    private Integer status; // 0-禁用 1-正常
    private LocalDateTime createTime;
}
