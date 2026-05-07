package com.enterprise.ems.mapper;

import com.enterprise.ems.entity.Position;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 职位Mapper接口
 */
@Mapper
public interface PositionMapper {

    List<Position> findAll();

    Position findById(Long id);

    int insert(Position position);

    int update(Position position);

    int deleteById(Long id);

    int countByName(String name);

    int countByNameExcludeId(String name, Long id);

    int countEmployeeByPositionId(Long positionId);
}
