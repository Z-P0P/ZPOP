package com.zpop.web.dto;

import com.zpop.web.entity.Region;

public class RegionDto {
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
	
	public Region toEntity() {
		Region region = new Region(name);
		region.setId(id);
		return region;
	}
}
