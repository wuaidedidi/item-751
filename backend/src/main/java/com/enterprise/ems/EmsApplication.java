package com.enterprise.ems;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 企业员工信息管理系统 - 启动类
 * Enterprise Employee Management System - Application Entry
 */
@SpringBootApplication
@MapperScan("com.enterprise.ems.mapper")
public class EmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmsApplication.class, args);
    }
}
