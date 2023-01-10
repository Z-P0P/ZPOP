package com.zpop.web.dto;

import com.zpop.web.entity.ContactType;

public class ContactTypeDto {
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
	
	public ContactType toEntity() {
		ContactType contactType = new ContactType(this.name);
		contactType.setId(id);
		return contactType;
	}
}
