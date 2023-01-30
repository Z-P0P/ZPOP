package com.zpop.web.dao;

import com.zpop.web.entity.ProfileFile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileFileDao {
    int insert(ProfileFile profileFile);
}
