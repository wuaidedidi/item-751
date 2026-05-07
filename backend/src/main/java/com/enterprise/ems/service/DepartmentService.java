package com.enterprise.ems.service;

import com.enterprise.ems.dto.DepartmentDTO;
import com.enterprise.ems.dto.DepartmentTreeNode;
import com.enterprise.ems.entity.Department;
import com.enterprise.ems.exception.BusinessException;
import com.enterprise.ems.mapper.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门服务
 */
@Service
public class DepartmentService {
    
    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取所有部门列表
     */
    public List<Department> findAll() {
        return departmentMapper.findAll();
    }

    /**
     * 获取部门树形结构
     */
    public List<DepartmentTreeNode> getTree() {
        List<Department> departments = departmentMapper.findAll();
        return buildTree(departments);
    }

    /**
     * 构建部门树
     */
    private List<DepartmentTreeNode> buildTree(List<Department> departments) {
        Map<Long, DepartmentTreeNode> nodeMap = departments.stream()
                .collect(Collectors.toMap(
                        Department::getId,
                        d -> new DepartmentTreeNode(d.getId(), d.getName(), d.getCode(), d.getParentId())
                ));

        List<DepartmentTreeNode> roots = new ArrayList<>();
        for (Department dept : departments) {
            DepartmentTreeNode node = nodeMap.get(dept.getId());
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                roots.add(node);
            } else {
                DepartmentTreeNode parent = nodeMap.get(dept.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                } else {
                    roots.add(node);
                }
            }
        }
        return roots;
    }

    /**
     * 根据ID获取部门
     */
    public Department findById(Long id) {
        Department department = departmentMapper.findById(id);
        if (department == null) {
            throw new BusinessException("部门不存在");
        }
        return department;
    }

    /**
     * 新增部门
     */
    public void create(DepartmentDTO dto) {
        logger.info("[新增部门] {}", dto.getName());
        
        if (departmentMapper.countByName(dto.getName()) > 0) {
            throw new BusinessException("部门名称已存在");
        }
        
        Department department = new Department();
        BeanUtils.copyProperties(dto, department);
        if (department.getStatus() == null) {
            department.setStatus(1);
        }
        if (department.getSortOrder() == null) {
            department.setSortOrder(0);
        }
        
        departmentMapper.insert(department);
        logger.info("[新增部门成功] id={}, name={}", department.getId(), department.getName());
    }

    /**
     * 更新部门
     */
    public void update(Long id, DepartmentDTO dto) {
        logger.info("[更新部门] id={}", id);
        
        Department existing = departmentMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("部门不存在");
        }
        
        if (departmentMapper.countByNameExcludeId(dto.getName(), id) > 0) {
            throw new BusinessException("部门名称已存在");
        }
        
        // 检查是否将自己设为父级
        if (dto.getParentId() != null && dto.getParentId().equals(id)) {
            throw new BusinessException("不能将自己设为上级部门");
        }
        
        BeanUtils.copyProperties(dto, existing);
        existing.setId(id);
        departmentMapper.update(existing);
        logger.info("[更新部门成功] id={}", id);
    }

    /**
     * 删除部门
     */
    public void delete(Long id) {
        logger.info("[删除部门] id={}", id);
        
        Department existing = departmentMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("部门不存在");
        }
        
        // 检查是否有子部门
        if (departmentMapper.countByParentId(id) > 0) {
            throw new BusinessException("该部门下存在子部门，无法删除");
        }
        
        // 检查是否有员工
        if (departmentMapper.countEmployeeByDepartmentId(id) > 0) {
            throw new BusinessException("该部门下存在员工，无法删除");
        }
        
        departmentMapper.deleteById(id);
        logger.info("[删除部门成功] id={}", id);
    }
}
