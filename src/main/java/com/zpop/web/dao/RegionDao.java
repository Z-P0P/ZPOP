package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.AdminRegionDto;
import com.zpop.web.entity.Region;

@Mapper
public interface RegionDao {
    Region get(int id);
    List<Region> getList();
    List<AdminRegionDto> getAdminViewList(int offset, int size, String keyword, String option);
    int count(String keyword, String option);
}
