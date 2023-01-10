package com.zpop.web.dto.admin;

import java.sql.Date;

public class AdminRegionDto {
	
	private int id;
	private String name;
	private int num;
	private Date deletedAt;
	
	public AdminRegionDto() {
	}
	
	public AdminRegionDto(int id, String name, int num, Date deletedAt) {
		this.id = id;
		this.name = name;
		this.num = num;
		this.deletedAt = deletedAt;
	}
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	
}
