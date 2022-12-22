package com.zpop.web.dao;

import com.zpop.web.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao {
    Category get(int id);
    List<Category> getList();
    
    Category getByName(String name);
}
