package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.CategoryDto;
import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.entity.Category;

@Mapper
public interface CategoryDao {
    Category get(int id);
    List<Category> getList();
    Category getByName(String name);
    List<AdminCategoryDto> getAdminViewList(int offset, int size, String keyword, String option);
	int count(String keyword, String option);
	List<CategoryDto> getActiveList();
}
