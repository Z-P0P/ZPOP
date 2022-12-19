package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.SocialType;

@Mapper
public interface SocialTypeDao {
    List<SocialType> getList();

    SocialType get(String name);

    int insert(SocialType socialType);

    int delete(SocialType socialType);

    int deleteAll(SocialType[] socialTypes);

    int update(SocialType socialType);

    int updateAll(SocialType[] socialTypes);

}
