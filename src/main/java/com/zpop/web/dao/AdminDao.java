package com.zpop.web.dao;

import com.zpop.web.entity.Admin;
import com.zpop.web.entity.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDao {
    Admin getByName(String name);
    int insert(Admin admin);
}
