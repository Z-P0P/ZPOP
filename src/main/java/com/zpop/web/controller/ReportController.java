package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dto.RequestCommentReportDto;
import com.zpop.web.dto.RequestMeetingReportDto;
import com.zpop.web.dto.RequestMemberReportDto;
import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.entity.ReportedMember;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	MeetingDao meetingDao;
	
	@PostMapping("meeting/{id}")
	@ResponseBody
	public String meeting(
			@PathVariable("id") int id,
			@RequestBody RequestMeetingReportDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails
			) {
		
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
	
	//댓글 신고 AJAX endpoint
	@PutMapping("comment/{id}")
	@ResponseBody
	public String reportComment(@PathVariable("id") int id, 
			@RequestBody ReportedComment reportedComment,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		
		// 중복 신고 
		int[] reportedComments = reportService.getCommentId(1,1);
		if(reportedComments.length > 0) {
			System.out.println("중복 신고하였습니다");
		}
		
		Comment comment = commentService.getCommentById(id);
		reportedComment.setCommentId(id);
		reportedComment.setReporterId(userDetails.getId());
		reportedComment.setOriginal(comment.getContent());
		reportService.createCommentReport(reportedComment);
		return "{\"1\":1}"; //JSON
	}
	
	@PostMapping("/member/{id}")
	@ResponseBody
	public String member(@RequestBody ReportedMember reportedMember) {
		
//		int reportTypeId = Integer.parseInt(dto.getReportType());
//		String reportReason = dto.getReportReason();
		
		reportService.createMemberReport(reportedMember);

		return "report/member";

	}
	
	
}
