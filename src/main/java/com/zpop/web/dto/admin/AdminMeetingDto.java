package com.zpop.web.dto.admin;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminMeetingDto {
	
	private int id;
	private String title;
	private String hostNickname;
	private int participantsNum;
	private int maxMember;
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	private boolean isClosed;
	private boolean isDeleted;

}
