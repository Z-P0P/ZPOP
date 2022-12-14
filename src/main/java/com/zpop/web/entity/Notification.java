package com.zpop.entity;

import java.util.Date;

public class Notification {
	
	private int id;
	private int memberId;
	private String url;
	private int type;
	private Date readAt;
	private Date createdAt;
	
	public Notification(int id, int memberId, String url, int type, Date readAt, Date createdAt) {
		super();
		this.id = id;
		this.memberId = memberId;
		this.url = url;
		this.type = type;
		this.readAt = readAt;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getReadAt() {
		return readAt;
	}

	public void setReadAt(Date readAt) {
		this.readAt = readAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", memberId=" + memberId + ", url=" + url + ", type=" + type + ", readAt="
				+ readAt + ", createdAt=" + createdAt + "]";
	}
	
	
}
