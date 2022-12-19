package com.zpop.web.dto;

import java.util.Date;

import com.zpop.web.entity.Meeting;

public class MeetingRegisterDto {
	
	
	
    private int regMemberId;
    private int categoryId;
    private int regionId;
    private int ageRangeId;
	private String title;
    private String content;
    private String detailRegion;
    private int maxMember;
    private Date startedAt;
    private String contact;
    
    public Meeting toEntity() {
    	return new Meeting(getRegMemberId(),getCategoryId(),
    			getRegionId(),getAgeRangeId(),
    			getTitle(),getContent(),
    			getDetailRegion(),getMaxMember(),
    			getStartedAt(),getContact());
    }
    
	public int getRegMemberId() {
		return regMemberId;
	}
	public void setRegMemberId(int regMemberId) {
		this.regMemberId = regMemberId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public int getAgeRangeId() {
		return ageRangeId;
	}
	public void setAgeRangeId(int ageRangeId) {
		this.ageRangeId = ageRangeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDetailRegion() {
		return detailRegion;
	}
	public void setDetailRegion(String detailRegion) {
		this.detailRegion = detailRegion;
	}
	public int getMaxMember() {
		return maxMember;
	}
	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}
	public Date getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	  @Override
		public String toString() {
			return "MeetingRegisterDto [regMemberId=" + regMemberId + ", categoryId=" + categoryId
					+ ", regionId=" + regionId + ", ageRangeId=" + ageRangeId + ", title=" + title + ", content=" + content
					+ ", detailRegion=" + detailRegion + ", maxMember=" + maxMember + ", startedAt=" + startedAt
					+ ", contact=" + contact + "]";
		}
}
