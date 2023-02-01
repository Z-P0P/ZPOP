package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.BannerDto;
import com.zpop.web.entity.Banner;

@Mapper
public interface BannerDao {
    
    List<Banner> getList(int offset, int size, String order);
    List<BannerDto> getActiveList();
    int update(Banner banner);
    int removeAll(List<Integer> ids);
    int insert(Banner banner);
}
