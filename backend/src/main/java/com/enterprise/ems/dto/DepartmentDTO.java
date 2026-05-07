package com.enterprise.ems.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 部门DTO
 */
@Data
public class DepartmentDTO {
    private Long id;

    @NotBlank(message = "部门名称不能为空")
    @Size(max = 50, message = "部门名称长度不能超过50个字符")
    private String name;

    @Size(max = 20, message = "部门编码长度不能超过20个字符")
    private String code;

    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    private Long parentId;

    private Integer sortOrder;

    private Integer status;
}
