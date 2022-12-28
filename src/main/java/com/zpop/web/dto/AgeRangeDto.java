package com.zpop.web.dto;

import com.zpop.web.entity.AgeRange;

public class AgeRangeDto {
	private int id;
	private String type;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public AgeRange toEntity() {
		AgeRange ageRange = new AgeRange(this.type);
		ageRange.setId(id);
		return ageRange;
	}
}
