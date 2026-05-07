package com.enterprise.ems.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 职位DTO
 */
@Data
public class PositionDTO {
    private Long id;

    @NotBlank(message = "职位名称不能为空")
    @Size(max = 50, message = "职位名称长度不能超过50个字符")
    private String name;

    @Size(max = 20, message = "职位编码长度不能超过20个字符")
    private String code;

    @Size(max = 200, message = "描述长度不能超过200个字符")
    private String description;

    private Integer sortOrder;

    private Integer status;
}
