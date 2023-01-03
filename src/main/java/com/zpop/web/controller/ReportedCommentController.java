package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.RequestCommentReportDto;
import com.zpop.web.entity.ReportedComment;
import com.zpop.web.security.ZpopUserDetails;
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
	public String comment(@RequestBody RequestCommentReportDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails
			) {
		
		int reportTypeId = Integer.parseInt(dto.getReportType());
		String reportReason = dto.getReportReason();
		
		// 중복 신고 
		int[] reportedComments = reportService.getCommentId(1,1);
		if(reportedComments.length > 0) {
			System.out.println("중복 신고하였습니다");
		}
		// 자신의 댓글 신고
		ReportedComment rm = new ReportedComment(
				userDetails.getId(),
				1,
				reportTypeId,
				reportReason,
				"original" // original
				);
		reportService.createCommentReport(rm);

		return "report/comment";

	}
	
	
}
