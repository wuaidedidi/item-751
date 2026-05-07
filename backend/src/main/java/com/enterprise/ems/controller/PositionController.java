package com.enterprise.ems.controller;

import com.enterprise.ems.common.Result;
import com.enterprise.ems.dto.PositionDTO;
import com.enterprise.ems.entity.Position;
import com.enterprise.ems.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 职位控制器
 */
@RestController
@RequestMapping("/api/positions")
public class PositionController {
    
    private static final Logger logger = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;

    /**
     * 获取所有职位列表
     */
    @GetMapping
    public Result<List<Position>> findAll() {
        logger.info("[API] GET /api/positions");
        List<Position> positions = positionService.findAll();
        return Result.success(positions);
    }

    /**
     * 根据ID获取职位
     */
    @GetMapping("/{id}")
    public Result<Position> findById(@PathVariable Long id) {
        logger.info("[API] GET /api/positions/{}", id);
        Position position = positionService.findById(id);
        return Result.success(position);
    }

    /**
     * 新增职位
     */
    @PostMapping
    public Result<Void> create(@Valid @RequestBody PositionDTO dto) {
        logger.info("[API] POST /api/positions - name: {}", dto.getName());
        positionService.create(dto);
        return Result.success("新增成功", null);
    }

    /**
     * 更新职位
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @Valid @RequestBody PositionDTO dto) {
        logger.info("[API] PUT /api/positions/{}", id);
        positionService.update(id, dto);
        return Result.success("更新成功", null);
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        logger.info("[API] DELETE /api/positions/{}", id);
        positionService.delete(id);
        return Result.success("删除成功", null);
    }
}
