package com.zpop.web.dao;

import java.util.List;

import com.zpop.web.entity.SocialType;

public interface SocialTypeDao {
    List<SocialType> getList();

    SocialType get(int id);

    int insert(SocialType socialType);

    int delete(SocialType socialType);

    int deleteAll(SocialType[] socialTypes);

    int update(SocialType socialType);

    int updateAll(SocialType[] socialTypes);

}
