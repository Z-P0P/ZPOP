package com.zpop.web.dto;

import java.util.Date;

public class MeetingDetailDto {
	
	private String title;
	private Date startedAt;
	private String detailRegion;
	private String content;
	private int viewCount;
	private String categoryName;
	private String regionName;
	private int maxMember;
	
	
	
	public int getMaxMember() {
		return maxMember;
	}
	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public String getDetailRegion() {
		return detailRegion;
	}
	public void setDetailRegion(String detailRegion) {
		this.detailRegion = detailRegion;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	@Override
	public String toString() {
		return "MeetingDetailDto [title=" + title + ", startedAt=" + startedAt + ", detailRegion=" + detailRegion
				+ ", content=" + content + ", viewCount=" + viewCount + ", categoryName=" + categoryName
				+ ", regionName=" + regionName + "]";
	}
	
	
}
