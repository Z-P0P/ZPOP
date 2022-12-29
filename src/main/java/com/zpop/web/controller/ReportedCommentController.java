package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.RequestCommentReportDto;
import com.zpop.web.dto.RequestMemberReportDto;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportedCommentController {
	
	@Autowired
	private ReportService reportService;
	

	@GetMapping("/comment")
	public String commentTest() {
		
		return"report/comment";
	}

	@PostMapping("/comment-test")
	@ResponseBody
	public String comment(@RequestBody RequestCommentReportDto dto) {
		
		int reportTypeId = Integer.parseInt(dto.getReportType());
		String reportReason = dto.getReportReason();
		
		reportService.createCommentReport(reportTypeId,reportReason);

		return "report/comment";

	}
	
}
