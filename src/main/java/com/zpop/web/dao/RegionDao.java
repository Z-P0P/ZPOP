package com.zpop.web.dao;

import com.zpop.web.entity.Region;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RegionDao {
    Region get(int id);
    List<Region> getList();
}
