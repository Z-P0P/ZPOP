package com.zpop.web.dao;

import com.zpop.web.dto.AgeRangeDto;
import com.zpop.web.entity.AgeRange;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgeRangeDao {
    AgeRange get(int id);
    List<AgeRange> getList();
	List<AgeRangeDto> getActiveList();
}
