package com.enterprise.ems.dto;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * 员工DTO
 */
@Data
public class EmployeeDTO {
    private Long id;

    @NotBlank(message = "工号不能为空")
    @Size(max = 20, message = "工号长度不能超过20个字符")
    private String employeeNo;

    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    private String name;

    @NotNull(message = "性别不能为空")
    private Integer gender;

    private LocalDate birthDate;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    @Size(max = 18, message = "身份证号长度不正确")
    private String idCard;

    @Size(max = 200, message = "地址长度不能超过200个字符")
    private String address;

    @NotNull(message = "部门不能为空")
    private Long departmentId;

    @NotNull(message = "职位不能为空")
    private Long positionId;

    private LocalDate hireDate;

    private Integer status;
}
