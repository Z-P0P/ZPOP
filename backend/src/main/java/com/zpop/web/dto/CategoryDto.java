package com.zpop.web.dto;

import com.zpop.web.entity.Category;

public class CategoryDto {
	private int id;
    private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
	public Category toEntity() {
		Category category = new Category(name);
		category.setId(id);
		return category;
	}
}
