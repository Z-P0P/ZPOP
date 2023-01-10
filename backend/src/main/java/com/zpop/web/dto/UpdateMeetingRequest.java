package com.zpop.web.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.zpop.web.entity.MeetingFile;
import com.zpop.web.entity.meeting.Meeting;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateMeetingRequest {

	private int meetingId;

	private int regMemberId;

	@Positive
	private int categoryId;

	@Positive
	private int regionId;

	@Positive
	private int ageRangeId;

	@Positive
	private int contactTypeId;

	@Positive
	private int genderCategory;

	@NotNull
	@NotBlank
	private String title;

	@NotNull
	@NotBlank
	private String content;

	@NotNull
	@NotBlank
	private String detailRegion;

	@Positive
	private int maxMember;

	@DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm") 
	@NotNull
	@Future
	private LocalDateTime startedAt;

	@NotNull
	@NotBlank
	private String contact;

	private List<MeetingFile> images;
	
	public UpdateMeetingRequest(int regMemberId, int categoryId, int regionId, int ageRangeId, int contactTypeId,
			int genderCategory, String title, String content, String detailRegion, int maxMember, LocalDateTime startedAt,
			String contact) {
		this.regMemberId = regMemberId;
		this.categoryId = categoryId;
		this.regionId = regionId;
		this.ageRangeId = ageRangeId;
		this.contactTypeId = contactTypeId;
		this.genderCategory = genderCategory;
		this.title = title;
		this.content = content;
		this.detailRegion = detailRegion;
		this.maxMember = maxMember;
		this.startedAt = startedAt;
		this.contact = contact;
	}


	public Meeting toEntity() {// DTO -> ENTITY HH:MM -> HH:MM:SS
		Date date = Date.from(startedAt.atZone(ZoneId.systemDefault()).toInstant());
		return new Meeting(getMeetingId(), getRegMemberId(), getCategoryId(), getRegionId(), getAgeRangeId(),getContactTypeId(),getGenderCategory(),getTitle(), getContent(),
				getDetailRegion(), getMaxMember(), date, getContact());
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


	public LocalDateTime getStartedAt() { //{hello  : world} -> 딕셔너리
		return startedAt;
	}

	public String getContact() {
		return contact;
	}

	@Override
	public String toString() {
		return "UpdateMeetingRequest [meetingId=" + meetingId + ", regMemberId=" + regMemberId + ", categoryId="
				+ categoryId + ", regionId=" + regionId + ", ageRangeId=" + ageRangeId + ", contactTypeId="
				+ contactTypeId + ", genderCategory=" + genderCategory + ", title=" + title + ", content=" + content
				+ ", detailRegion=" + detailRegion + ", maxMember=" + maxMember + ", startedAt=" + startedAt
				+ ", contact=" + contact + "]";
	}

	public int getContactTypeId() {
		return contactTypeId;
	}

	public int getGenderCategory() {
		return genderCategory;
	}

	public void setRegMemberId(int regMemberId) {
		this.regMemberId = regMemberId;
	}
	
	public void setContent(String content) {
		this.content = content;
	}


	public int getMeetingId() {
		return meetingId;
	}


	public void setMeetingId(int meetingId) {
		this.meetingId = meetingId;
	}


	public List<MeetingFile> getImages() {
		return images;
	}


	public void setImages(List<MeetingFile> images) {
		this.images = images;
	}

}
