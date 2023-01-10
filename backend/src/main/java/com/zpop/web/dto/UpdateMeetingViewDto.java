package com.zpop.web.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class UpdateMeetingViewDto {

	private String title;
	@DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm") 
	private LocalDateTime startedAt;	
	private String detailRegion;
	private String content;
	private int categoryId;
	private String categoryName;
	private int regionId;
	private String regionName;
	private int genderCategory;
	private int ageRangeId;
	private String ageRangeType;
	private int contactTypeId;
	private String contactTypeName;
	private String contact;
	private int maxMember;
	private int regMemberId;
	
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
	public int getGenderCategory() {
		return genderCategory;
	}
	public void setGenderCategory(int genderCategory) {
		this.genderCategory = genderCategory;
	}
	public int getAgeRangeId() {
		return ageRangeId;
	}
	public void setAgeRangeId(int ageRangeId) {
		this.ageRangeId = ageRangeId;
	}
	public String getAgeRangeType() {
		return ageRangeType;
	}
	public void setAgeRangeType(String ageRangeType) {
		this.ageRangeType = ageRangeType;
	}
	public int getContactTypeId() {
		return contactTypeId;
	}
	public void setContactTypeId(int contactTypeId) {
		this.contactTypeId = contactTypeId;
	}
	public String getContactTypeName() {
		return contactTypeName;
	}
	public void setContactTypeName(String contactTypeName) {
		this.contactTypeName = contactTypeName;
	}
	
	public int getRegMemberId() {
		return regMemberId;
	}
	public void setRegMemberId(int regMemberId) {
		this.regMemberId = regMemberId;
	}

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
	public LocalDateTime getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(LocalDateTime startedAt) {
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
		return "UpdateMeetingViewResponse [title=" + title + ", startedAt=" + startedAt + ", detailRegion="
				+ detailRegion + ", content=" + content + ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", regionId=" + regionId + ", regionName=" + regionName + ", genderCategory="
				+ genderCategory + ", ageRangeId=" + ageRangeId + ", ageRangeType=" + ageRangeType + ", contactTypeId="
				+ contactTypeId + ", contactTypeName=" + contactTypeName + ", maxMember=" + maxMember + ", regMemberId=" + regMemberId +  "]";
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
}
