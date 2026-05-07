package com.enterprise.ems.controller;

import com.enterprise.ems.common.Result;
import com.enterprise.ems.entity.Department;
import com.enterprise.ems.entity.Position;
import com.enterprise.ems.service.DepartmentService;
import com.enterprise.ems.service.EmployeeService;
import com.enterprise.ems.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘控制器
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PositionService positionService;

    /**
     * 获取仪表盘统计数据
     */
    @GetMapping
    public Result<Map<String, Object>> getDashboard() {
        logger.info("[API] GET /api/dashboard");
        
        Map<String, Object> dashboard = new HashMap<>();
        
        // 员工统计
        Map<String, Object> employeeStats = employeeService.getStatistics();
        dashboard.put("employeeStats", employeeStats);
        
        // 部门列表
        List<Department> departments = departmentService.findAll();
        dashboard.put("departmentCount", departments.size());
        
        // 职位列表
        List<Position> positions = positionService.findAll();
        dashboard.put("positionCount", positions.size());

        return Result.success(dashboard);
    }
}
