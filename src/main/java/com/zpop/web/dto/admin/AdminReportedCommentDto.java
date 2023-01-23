package com.zpop.web.dto.admin;

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
public class AdminReportedCommentDto {
	
	private int id;
	private String reporterNickname;
	private int reporterId;
	private String original;
	private String writerNickname;
	private int writerId;
	private String reportType;
	private String reason;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	private Date processedAt;
}
