package com.zpop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.service.CommentService;

@Controller
//@RequestMapping("/")
public class CommentController {
	@Autowired
	CommentService service;
	
	@GetMapping("/comment")
	public String comment(Model model) {
		//개발시 임시로 하드코딩한 값
		int meetingId = 1;
		List<CommentView> comments = service.getComment(meetingId);
		int countOfComment = service.getCountOfComment(meetingId);
		
		model.addAttribute("comments", comments);
		model.addAttribute("countOfComment", countOfComment);
		
		return "comment/comment";
		
	}
	
	@GetMapping("/reply")
	public String reply(Model model) {
		//개발시 임시로 하드코딩한 값
		int groupId = 4;
		List<CommentView> replies = service.getReply(groupId);
		model.addAttribute("replies", replies);
		return "comment/reply";
	}
	

	
	@PostMapping("comment")
	public  String registerComment(@RequestBody Comment comment) {
		service.registerComment(comment);
		return "comment/comment";
	}
	@PostMapping("reply")
	public  String registerReply(@RequestBody Comment comment) {
		service.registerReply(comment);
		return "comment/reply";
	}
}
