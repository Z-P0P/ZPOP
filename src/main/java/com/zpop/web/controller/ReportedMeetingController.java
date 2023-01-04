package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dto.RequestMeetingReportDto;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportedMeetingController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	MeetingDao meetingDao;
		
	    
	@GetMapping("meeting")
	public String meetingTest() {
		
		return"report/meeting";
	}

	@PostMapping("{id}")
	@ResponseBody
	public String meeting(
			@PathVariable("id") int id,
			@RequestBody RequestMeetingReportDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails
			) {
		System.out.println("-"+id);
		int reportTypeId = Integer.parseInt(dto.getReportType());
		String reportReason = dto.getReportReason();
		Meeting meeting = meetingDao.get(id);
		
		ReportedMeeting rp = new ReportedMeeting(
								id, // meetingId
								userDetails.getId(), // reporterId
								reportTypeId, // reportTypeId
								reportReason, // reportReason
								meeting.getTitle(), // originalTitle
								meeting.getContent() // original
								);
		reportService.createMeetingReport(rp);

		return "report/meeting";

	}
	
}