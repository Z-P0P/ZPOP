package com.zpop.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.entity.Member;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.service.CommentService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class CommentController {
	@Autowired
	private CommentService service;
	

	//답글 AJAX endpoint (js에서 콜하는 함수)
	@GetMapping("comment/{groupId}/reply")
	@ResponseBody
	public  Map<String, Object> getReply(@PathVariable int groupId) {

		List<CommentView> replies = service.getReply(groupId);
		int countOfReply = service.getCountOfReply(groupId);
		
		Map<String,Object> dto = new HashMap<>();
		dto.put("status",200);
		dto.put("resultObject",replies);
		dto.put("countOfReply",countOfReply);
		
		return dto;
	}

	//댓글(=comment) 등록
	@PostMapping("comment")
	public  String registerComment(@RequestBody Comment comment, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		comment.setWriterId(member.getId());
		service.registerComment(comment);
		return "comment/comment";
	}
	//답글(=reply) 등록
	@PostMapping("reply")
	public  String registerReply(@RequestBody Comment comment, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		comment.setWriterId(member.getId());
		service.registerReply(comment);
		return "comment/reply";
	}
}
