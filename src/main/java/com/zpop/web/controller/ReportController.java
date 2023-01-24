package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dto.RequestMeetingReportDto;
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
	
	//모임신고
	@PostMapping("meeting/{id}")
	@ResponseBody
	public boolean meeting(
			@PathVariable("id") int id,
			@RequestBody RequestMeetingReportDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails
			) {
		boolean result;
		
		
		int[] list = reportService.getReportedMeetingId(id, userDetails.getId());
		if(list.length==0) {
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
			result = true;
		}
		else {
			result = false;
		}
		return result;
	}
	
	//댓글 신고 AJAX endpoint
	@PostMapping("comment/{id}")
	@ResponseBody
	public boolean reportComment(@PathVariable("id") int id, 
			@RequestBody ReportedComment reportedComment,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		boolean result;
		Comment comment = commentService.getCommentById(id);
		
		// 중복 신고 
		int[] reportedComments = reportService.getReportedCommentId(id,userDetails.getId());
		
		if(reportedComments.length == 0) {
			reportedComment.setCommentId(id);
			reportedComment.setReporterId(userDetails.getId());
			reportedComment.setOriginal(comment.getContent());
			reportService.createCommentReport(reportedComment);
			result = true;
		}
		else {
			result = false;
		}
		
		return result; //JSON
	}
	
	// 사용자 신고
	@PostMapping("/member/{memberId}")
	@ResponseBody
	public boolean member(
			@PathVariable("memberId") int memberId,
			@RequestBody ReportedMember reportedMember,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		boolean result;
		
		// 피신고자, 신고자, 유형, 사유
		reportedMember.setReportedId(memberId);
		reportedMember.setReporterId(userDetails.getId());
		reportService.createMemberReport(reportedMember);
		result = true;

		return result;
	}
}
