package com.zpop.web.dto;

public class NotificationDto {
	
	private String id;
	private String readAt;
	private String type;
	
	public NotificationDto() {

	}
	
	public NotificationDto(String id, String readAt, String type) {
		this.id = id;
		this.readAt = readAt;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReadAt() {
		return readAt;
	}

	public void setReadAt(String readAt) {
		this.readAt = readAt;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	

}
