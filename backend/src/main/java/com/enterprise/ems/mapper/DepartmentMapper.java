package com.enterprise.ems.mapper;

import com.enterprise.ems.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 部门Mapper接口
 */
@Mapper
public interface DepartmentMapper {

    List<Department> findAll();

    Department findById(Long id);

    List<Department> findByParentId(Long parentId);

    int insert(Department department);

    int update(Department department);

    int deleteById(Long id);

    int countByName(String name);

    int countByNameExcludeId(String name, Long id);

    int countByParentId(Long parentId);

    int countEmployeeByDepartmentId(Long departmentId);
}
