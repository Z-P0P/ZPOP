package com.zpop.web.dto.admin;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AdminMeetingDetailsDto {
	private int id;
	private int regMemberId;
	private String regMemberNickname;
	private String regMemberProfile;
	private int categoryId;
	private String categoryName;
	private int regionId;
	private String regionName;
	private int ageRangeId;
	private String ageRangeType;
	private int contactTypeId;
	private String contactTypeName;
	private int genderCategory;
	private String title;
	private String content;
	private String detailRegion;
	private int maxMember;
	private String contact;
	private int viewCount;
	private int commentCount;
	private boolean isClosed;
	private boolean isDeleted;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date startedAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date deletedAt;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date closedAt;

	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRegMemberId() {
		return regMemberId;
	}
	public void setRegMemberId(int regMemberId) {
		this.regMemberId = regMemberId;
	}
	public String getRegMemberNickname() {
		return regMemberNickname;
	}
	public void setRegMemberNickname(String regMemberNickname) {
		this.regMemberNickname = regMemberNickname;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
	public int getGenderCategory() {
		return genderCategory;
	}
	public void setGenderCategory(int genderCategory) {
		this.genderCategory = genderCategory;
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
	public int getViewCount() {
		return viewCount;
	}
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public Date getClosedAt() {
		return closedAt;
	}
	public void setClosedAt(Date closedAt) {
		this.closedAt = closedAt;
	}

	@Override
	public String toString() {
		return "AdminDetailMeetingDto [id=" + id + ", regMemberId=" + regMemberId + ", regMemberNickname="
				+ regMemberNickname + ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", regionId="
				+ regionId + ", regionName=" + regionName + ", ageRangeId=" + ageRangeId + ", ageRangeType="
				+ ageRangeType + ", contactTypeId=" + contactTypeId + ", contactTypeName=" + contactTypeName
				+ ", genderCategory=" + genderCategory + ", title=" + title + ", content=" + content + ", detailRegion="
				+ detailRegion + ", maxMember=" + maxMember + ", startedAt=" + startedAt + ", contact=" + contact
				+ ", viewCount=" + viewCount + ", commentCount=" + commentCount + ", isClosed=" + isClosed
				+ ", isDeleted=" + isDeleted + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt="
				+ deletedAt + ", closedAt=" + closedAt + "]";
	}

	public String getRegMemberProfile() {
		return regMemberProfile;
	}

	public void setRegMemberProfile(String regMemberProfile) {
		this.regMemberProfile = regMemberProfile;
	}
}
