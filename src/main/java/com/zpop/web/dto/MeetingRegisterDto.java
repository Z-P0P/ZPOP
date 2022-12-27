package com.zpop.web.dto;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zpop.web.entity.meeting.Meeting;

public class MeetingRegisterDto {

	private int regMemberId;
	private int categoryId;
	private int regionId;
	private int ageRangeId;
	private int contactTypeId;
	private int genderCategory;
	private String title;
	private String content;
	private String detailRegion;
	private int maxMember;
	private Date startedAt;
	private String contact;
	public Meeting toEntity() {// DTO -> ENTITY HH:MM -> HH:MM:SS
		return new Meeting(getRegMemberId(), getCategoryId(), getRegionId(), getAgeRangeId(),getContactTypeId(),getGenderCategory(),getTitle(), getContent(),
				getDetailRegion(), getMaxMember(), getStartedAt(), getContact());
	}

	public int getRegMemberId() {
		return regMemberId;
	}


	public int getCategoryId() {
		return categoryId;
	}


	public int getRegionId() {
		return regionId;
	}


	public int getAgeRangeId() {
		return ageRangeId;
	}


	public String getTitle() {
		return title;
	}


	public String getContent() {
		return content;
	}


	public String getDetailRegion() {
		return detailRegion;
	}


	public int getMaxMember() {
		return maxMember;
	}


	public Date getStartedAt() { //{hello  : world} -> 딕셔너리
		return startedAt;
	}

	/*
	 * public void setStartedAt(Date startedAt) throws ParseException {
	 * System.out.println(startedAt); DateFormat formatter = new
	 * SimpleDateFormat("yyyy-MM-dd'T'hh:mm"); Date date =
	 * (Date)formatter.parse(startedAt); System.out.println(date);
	 * 
	 * this.startedAt = startedAt; }
	 */
	public String getContact() {
		return contact;
	}




	@Override
	public String toString() {
		return "MeetingRegisterDto [regMemberId=" + regMemberId + ", categoryId=" + categoryId + ", regionId="
				+ regionId + ", ageRangeId=" + ageRangeId + ", contactTypeId=" + contactTypeId + ", genderCategory="
				+ genderCategory + ", title=" + title + ", content=" + content + ", detailRegion=" + detailRegion
				+ ", maxMember=" + maxMember + ", startedAt=" + startedAt + ", contact=" + contact + "]";
	}

	public int getContactTypeId() {
		return contactTypeId;
	}

	public int getGenderCategory() {
		return genderCategory;
	}

	
}
