package com.enterprise.ems.dto;

import lombok.Data;
import java.util.List;
import java.util.ArrayList;

/**
 * 部门树形结构节点
 */
@Data
public class DepartmentTreeNode {
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private List<DepartmentTreeNode> children = new ArrayList<>();

    public DepartmentTreeNode() {}

    public DepartmentTreeNode(Long id, String name, String code, Long parentId) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.parentId = parentId;
    }
}
