package com.enterprise.ems.service;

import com.enterprise.ems.dto.PositionDTO;
import com.enterprise.ems.entity.Position;
import com.enterprise.ems.exception.BusinessException;
import com.enterprise.ems.mapper.PositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职位服务
 */
@Service
public class PositionService {
    
    private static final Logger logger = LoggerFactory.getLogger(PositionService.class);

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 获取所有职位列表
     */
    public List<Position> findAll() {
        return positionMapper.findAll();
    }

    /**
     * 根据ID获取职位
     */
    public Position findById(Long id) {
        Position position = positionMapper.findById(id);
        if (position == null) {
            throw new BusinessException("职位不存在");
        }
        return position;
    }

    /**
     * 新增职位
     */
    public void create(PositionDTO dto) {
        logger.info("[新增职位] {}", dto.getName());
        
        if (positionMapper.countByName(dto.getName()) > 0) {
            throw new BusinessException("职位名称已存在");
        }
        
        Position position = new Position();
        BeanUtils.copyProperties(dto, position);
        if (position.getStatus() == null) {
            position.setStatus(1);
        }
        if (position.getSortOrder() == null) {
            position.setSortOrder(0);
        }
        
        positionMapper.insert(position);
        logger.info("[新增职位成功] id={}, name={}", position.getId(), position.getName());
    }

    /**
     * 更新职位
     */
    public void update(Long id, PositionDTO dto) {
        logger.info("[更新职位] id={}", id);
        
        Position existing = positionMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("职位不存在");
        }
        
        if (positionMapper.countByNameExcludeId(dto.getName(), id) > 0) {
            throw new BusinessException("职位名称已存在");
        }
        
        BeanUtils.copyProperties(dto, existing);
        existing.setId(id);
        positionMapper.update(existing);
        logger.info("[更新职位成功] id={}", id);
    }

    /**
     * 删除职位
     */
    public void delete(Long id) {
        logger.info("[删除职位] id={}", id);
        
        Position existing = positionMapper.findById(id);
        if (existing == null) {
            throw new BusinessException("职位不存在");
        }
        
        // 检查是否有员工
        if (positionMapper.countEmployeeByPositionId(id) > 0) {
            throw new BusinessException("该职位下存在员工，无法删除");
        }
        
        positionMapper.deleteById(id);
        logger.info("[删除职位成功] id={}", id);
    }
}
