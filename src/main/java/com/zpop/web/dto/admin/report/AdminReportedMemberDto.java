package com.zpop.web.dto.admin.report;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminReportedMemberDto {
	
	private int id;
	private String reporterNickname;
	private int reporterId;
	private String reportedNickname;
	private int reportedId;
	private String reportType;
	private String reason;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	private Date processedAt;
	
}
