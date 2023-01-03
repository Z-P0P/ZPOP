package com.zpop.web.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.RequestMeetingReportDto;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportedMeetingController {
	
	@Autowired
	private ReportService reportService;
		
	    
	@GetMapping("/meeting")
	public String meetingTest() {	
		
		return"report/meeting";
	}

	@PostMapping("/meeting-test")
	@ResponseBody
	public String meeting(
			@RequestBody RequestMeetingReportDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails
			) {
		
		int reportTypeId = Integer.parseInt(dto.getReportType());
		String reportReason = dto.getReportReason();
		
		ReportedMeeting rp = new ReportedMeeting(
								1, // meetingId
								userDetails.getId(), // reporterId
								reportTypeId, // reportTypeId
								reportReason, // reportReason
								"원제", // originalTitle
								"필드" // original
								);
		reportService.createMeetingReport(rp);

		return "report/meeting";

	}
	
}