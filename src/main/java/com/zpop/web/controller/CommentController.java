package com.zpop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zpop.web.entity.Comment;
import com.zpop.web.service.CommentService;

@Controller
@RequestMapping("/")
public class CommentController {
	@Autowired
	CommentService service;
	
	@GetMapping("comment")
	public String comment(Model model) {
		int meetingId = 1;
		int groupId = 1;
		List<Comment> comment = service.getComment(meetingId);
		List<Comment> reply = service.getReply(groupId);
		
		model.addAttribute("comment-list", comment);
		model.addAttribute("reply-list", reply);
		
		
		return "test/comment-test";
		
	}

}
