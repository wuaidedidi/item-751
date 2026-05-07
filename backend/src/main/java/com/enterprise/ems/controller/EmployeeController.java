package com.enterprise.ems.controller;

import com.enterprise.ems.common.PageRequest;
import com.enterprise.ems.common.Result;
import com.enterprise.ems.dto.EmployeeDTO;
import com.enterprise.ems.dto.PageResult;
import com.enterprise.ems.entity.Employee;
import com.enterprise.ems.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 员工控制器
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    /**
     * 分页查询员工列表
     */
    @GetMapping
    public Result<PageResult<Employee>> findByPage(PageRequest request) {
        logger.info("[API] GET /api/employees - page: {}, size: {}, keyword: {}", 
                request.getPageNum(), request.getPageSize(), request.getKeyword());
        PageResult<Employee> result = employeeService.findByPage(request);
        return Result.success(result);
    }

    /**
     * 根据ID获取员工
     */
    @GetMapping("/{id}")
    public Result<Employee> findById(@PathVariable Long id) {
        logger.info("[API] GET /api/employees/{}", id);
        Employee employee = employeeService.findById(id);
        return Result.success(employee);
    }

    /**
     * 新增员工
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody EmployeeDTO dto) {
        logger.info("[API] POST /api/employees - employeeNo: {}, name: {}", dto.getEmployeeNo(), dto.getName());
        employeeService.create(dto);
        return Result.success("新增成功", null);
    }

    /**
     * 更新员工
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        logger.info("[API] PUT /api/employees/{}", id);
        employeeService.update(id, dto);
        return Result.success("更新成功", null);
    }

    /**
     * 删除员工
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        logger.info("[API] DELETE /api/employees/{}", id);
        employeeService.delete(id);
        return Result.success("删除成功", null);
    }

    /**
     * 获取统计数据
     */
    @GetMapping("/statistics")
    public Result<Map<String, Object>> getStatistics() {
        logger.info("[API] GET /api/employees/statistics");
        Map<String, Object> stats = employeeService.getStatistics();
        return Result.success(stats);
    }
}
