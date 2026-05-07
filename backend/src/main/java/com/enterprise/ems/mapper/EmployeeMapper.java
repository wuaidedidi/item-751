package com.enterprise.ems.mapper;

import com.enterprise.ems.common.PageRequest;
import com.enterprise.ems.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 员工Mapper接口
 */
@Mapper
public interface EmployeeMapper {

    List<Employee> findByPage(@Param("request") PageRequest request, @Param("offset") int offset);

    long countByCondition(@Param("request") PageRequest request);

    Employee findById(Long id);

    int insert(Employee employee);

    int update(Employee employee);

    int deleteById(Long id);

    int countByEmployeeNo(String employeeNo);

    int countByEmployeeNoExcludeId(String employeeNo, Long id);

    int countAll();

    int countByStatus(Integer status);

    int countByDepartmentId(Long departmentId);
}
