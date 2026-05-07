package com.enterprise.ems.controller;

import com.enterprise.ems.common.Result;
import com.enterprise.ems.dto.DepartmentDTO;
import com.enterprise.ems.dto.DepartmentTreeNode;
import com.enterprise.ems.entity.Department;
import com.enterprise.ems.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 部门控制器
 */
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    
    private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门列表
     */
    @GetMapping
    public Result<List<Department>> findAll() {
        logger.info("[API] GET /api/departments");
        List<Department> departments = departmentService.findAll();
        return Result.success(departments);
    }

    /**
     * 获取部门树形结构
     */
    @GetMapping("/tree")
    public Result<List<DepartmentTreeNode>> getTree() {
        logger.info("[API] GET /api/departments/tree");
        List<DepartmentTreeNode> tree = departmentService.getTree();
        return Result.success(tree);
    }

    /**
     * 根据ID获取部门
     */
    @GetMapping("/{id}")
    public Result<Department> findById(@PathVariable Long id) {
        logger.info("[API] GET /api/departments/{}", id);
        Department department = departmentService.findById(id);
        return Result.success(department);
    }

    /**
     * 新增部门
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody DepartmentDTO dto) {
        logger.info("[API] POST /api/departments - name: {}", dto.getName());
        departmentService.create(dto);
        return Result.success("新增成功", null);
    }

    /**
     * 更新部门
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody DepartmentDTO dto) {
        logger.info("[API] PUT /api/departments/{}", id);
        departmentService.update(id, dto);
        return Result.success("更新成功", null);
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        logger.info("[API] DELETE /api/departments/{}", id);
        departmentService.delete(id);
        return Result.success("删除成功", null);
    }
}
