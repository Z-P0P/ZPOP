package com.zpop.web.dto.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
