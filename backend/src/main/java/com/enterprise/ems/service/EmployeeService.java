package com.enterprise.ems.service;

import com.enterprise.ems.common.PageRequest;
import com.enterprise.ems.dto.EmployeeDTO;
import com.enterprise.ems.dto.PageResult;
import com.enterprise.ems.entity.Employee;
import com.enterprise.ems.exception.BusinessException;
import com.enterprise.ems.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工服务
 */
@Service
public class EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 分页查询员工列表
     */
    public PageResult<Employee> findByPage(PageRequest request) {
        int offset = (request.getPageNum() - 1) * request.getPageSize();
        List<Employee> list = employeeMapper.findByPage(request, offset);
        long total = employeeMapper.countByCondition(request);
        return new PageResult<>(list, total, request.getPageNum(), request.getPageSize());
    }

    /**
     * 根据ID获取员工
     */
    public Employee findById(Long id) {
        Employee employee = employeeMapper.findById(id);
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }
        return employee;
    }

    /**
     * 新增员工
     */
    public void create(EmployeeDTO dto) {
        logger.info("[新增员工] 工号={}, 姓名={}", dto.getEmployeeNo(), dto.getName());
        
        if (employeeMapper.countByEmployeeNo(dto.getEmployeeNo()) > 0) {
            throw new BusinessException("员工工号已存在");
        }
        
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto, employee);
        if (employee.getStatus() == null) {
            employee.setStatus(1);
        }
        
        employeeMapper.insert(employee);
        logger.info("[新增员工成功] id={}, 工号={}", employee.getId(), employee.getEmployeeNo());
    }

    /**
     * 更新员工
     */
    public void update(Long id, EmployeeDTO dto) {
        logger.info("[更新员工] id={}", id);
        
        Employee existing = employeeMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("员工不存在");
        }
        
        if (employeeMapper.countByEmployeeNoExcludeId(dto.getEmployeeNo(), id) > 0) {
            throw new BusinessException("员工工号已存在");
        }
        
        BeanUtils.copyProperties(dto, existing);
        existing.setId(id);
        employeeMapper.update(existing);
        logger.info("[更新员工成功] id={}", id);
    }

    /**
     * 删除员工
     */
    public void delete(Long id) {
        logger.info("[删除员工] id={}", id);
        
        Employee existing = employeeMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("员工不存在");
        }
        
        employeeMapper.deleteById(id);
        logger.info("[删除员工成功] id={}", id);
    }

    /**
     * 获取统计数据
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", employeeMapper.countAll());
        stats.put("active", employeeMapper.countByStatus(1));
        stats.put("inactive", employeeMapper.countByStatus(0));
        return stats;
    }
}
