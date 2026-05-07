package com.enterprise.ems.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体
 */
@Data
public class Employee {
    private Long id;
    private String employeeNo;
    private String name;
    private Integer gender; // 0-女 1-男
    private LocalDate birthDate;
    private String phone;
    private String email;
    private String idCard;
    private String address;
    private Long departmentId;
    private Long positionId;
    private LocalDate hireDate;
    private Integer status; // 0-离职 1-在职
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    
    // 关联字段
    private String departmentName;
    private String positionName;
}
