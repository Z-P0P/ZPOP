package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.RequestMemberReportDto;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportedMemberController {
	
	@Autowired
	private ReportService reportService;
	

	@GetMapping("/member")
	public String memberTest() {
		
		return"report/member";
	}

	@PostMapping("/member-test")
	@ResponseBody
	public String member(@RequestBody RequestMemberReportDto dto) {
		
		int reportTypeId = Integer.parseInt(dto.getReportType());
		String reportReason = dto.getReportReason();
		
		//reportService.createMemberReport(reportTypeId,reportReason);

		return "report/member";

	}
	
}
