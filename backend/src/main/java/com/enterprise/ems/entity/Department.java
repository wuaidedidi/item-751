package com.enterprise.ems.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 部门实体
 */
@Data
public class Department {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Long parentId;
    private Integer sortOrder;
    private Integer status; // 0-禁用 1-正常
    private LocalDateTime createTime;
    
    // 关联字段
    private String parentName;
}
