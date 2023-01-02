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

import com.zpop.web.entity.ReportedComment;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.ReportService;

@Controller
@RequestMapping("/reply")
public class ReplyController {
	@Autowired
	private CommentService service;
	@Autowired
	private ReportService reportService;
	
	//답글(reply)불러오기 AJAX endpoint (js에서 콜하는 함수)
	@GetMapping()
	@ResponseBody
	public  Map<String, Object> getReply(@RequestParam int groupId,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		List<CommentView> replies = null;
		int memberId = 0;
		if(userDetails!=null) 
			memberId = userDetails.getId();
		if(memberId != 0)
			replies = service.getReplyWithWriter(memberId, groupId);
		else
			replies = service.getReply(groupId);
		int countOfReply = service.getCountOfReply(groupId);
		
		Map<String,Object> dto = new HashMap<>();
		dto.put("status",200);
		dto.put("resultObject",replies);
		dto.put("countOfReply",countOfReply);
		
		return dto;
	}

	//답글(reply) 등록 AJAX endpoint (js에서 콜하는 함수)
	@PostMapping()
	public  String registerReply(@RequestBody Comment reply, 
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		reply.setWriterId(userDetails.getId());
		service.registerReply(reply);
		return "comment/reply";
	}
	//댓글 수정 AJAX endpoint
		@PatchMapping("{id}")
		@ResponseBody
		public int updateReply(@PathVariable int id, @RequestBody Comment editedReply,
				@AuthenticationPrincipal ZpopUserDetails userDetails) {
			service.updateComment(editedReply);
			Comment reply = service.getCommentById(id);
			return reply.getGroupId();
		}
		//댓글 삭제 AJAX endpoint
		@DeleteMapping("{id}")
		@ResponseBody
		public int deleteReply(@PathVariable int id,
				@AuthenticationPrincipal ZpopUserDetails userDetails) {
			service.deleteComment(id);
			Comment reply = service.getCommentById(id);
			return reply.getGroupId();
		}
		//댓글 신고 AJAX endpoint
		@PutMapping("{id}")
		@ResponseBody
		public String reportReply(@PathVariable int id, @RequestBody ReportedComment reportedReply,
				@AuthenticationPrincipal ZpopUserDetails userDetails) {
			Comment reply = service.getCommentById(id);
			reportedReply.setReporterId(userDetails.getId());
			reportedReply.setOriginal(reply.getContent());
			reportService.createCommentReport(reportedReply);
			return "{\"1\":1}"; //JSON
		}
}
