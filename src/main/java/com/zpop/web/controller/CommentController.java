package com.zpop.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.RequestCommentReportDto;
import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService service;
	@Autowired
	private ReportService reportService;
	
	//댓글(comment)불러오기 AJAX endpoint (js에서 콜하는 함수)
	@GetMapping()
	@ResponseBody
	public  Map<String, Object> getComment(@RequestParam int meetingId) {
		
		List<CommentView> comments = service.getComment(meetingId);
		int countOfComment = service.getCountOfComment(meetingId);
		
		Map<String,Object> dto = new HashMap<>();
		dto.put("status",200);
		dto.put("resultObject",comments);
		dto.put("countOfComment",countOfComment);
		
		return dto;
	}
	
	//댓글(comment) 등록 AJAX endpoint (js에서 콜하는 함수)
	@PostMapping()
	@ResponseBody
	public  boolean registerComment(@RequestBody Comment comment, 
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		comment.setWriterId(userDetails.getId());
		service.registerComment(comment);
		return true;
	}
	
	//댓글 수정 AJAX endpoint
	@PatchMapping("{id}")
	@ResponseBody
	public String updateComment(@PathVariable int id, @RequestBody Comment comment,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		service.updateComment(comment);
		return "{\"1\":1}"; //JSON
	}
	//댓글 삭제 AJAX endpoint
	@DeleteMapping("{id}")
	@ResponseBody
	public String deleteComment(@PathVariable int id, 
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		service.deleteComment(id);
		return "{\"1\":1}"; //JSON
	}
	//댓글 신고 AJAX endpoint
	@PostMapping("{id}")
	@ResponseBody
	public String reportComment(@PathVariable int id, 
			@RequestBody ReportedComment reportedComment,
			@RequestBody RequestCommentReportDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		
		Comment comment = service.getCommentById(id);
		reportedComment.setCommentId(id);
		reportedComment.setReporterId(userDetails.getId());
		reportedComment.setTypeId(Integer.parseInt(dto.getReportType()));
		reportedComment.setReason(dto.getReportReason());
		reportedComment.setOriginal(comment.getContent());
		reportService.createCommentReport(reportedComment);
		return "{\"1\":1}"; //JSON
	}
}
