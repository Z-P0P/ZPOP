package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportedMeetingController {
	
	@Autowired
	private ReportService reportService;

	@PostMapping("/meeting")
	@ResponseBody
	public String meeting(@RequestBody RequestMeetingReportDto dto) {
		
		int reportTypeId = Integer.parseInt(dto.getReportType());
		String reportReason = dto.getReportReason();
		
		reportService.createMeetingReport(reportTypeId,reportReason);

		return "report/meeting";

	}

	@GetMapping("/test")
	public String meetingTest() {
		
		return"report/meeting";
	}
	
}

class RequestMeetingReportDto {

	private String reportType;
	private String reportReason;

	RequestMeetingReportDto(){

	}

	public RequestMeetingReportDto(String reportType, String reportReason) {
		this.reportType = reportType;
		this.reportReason = reportReason;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportReason() {
		return reportReason;
	}

	public void setReportReason(String reportReason) {
		this.reportReason = reportReason;
	}

	@Override
	public String toString() {
		return "RequestMeetingReportDto [reportType=" + reportType + ", reportReason=" + reportReason + "]";
	}

}