package com.zpop.web.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zpop.web.entity.CommentView;
import com.zpop.web.service.CommentService;

@Controller
@RequestMapping("/")
public class CommentController {
	@Autowired
	CommentService service;
	
	@GetMapping("comment")
	public String comment(Model model) {
		int meetingId = 1;
		List<CommentView> comments = service.getComment(meetingId);
		int countOfComment = service.getCountOfComment(meetingId);
		
		model.addAttribute("comments", comments);
		model.addAttribute("countOfComment", countOfComment);
		
		return "comment/comment";
		
	}
	@GetMapping("reply")
	public String reply(Model model) {
		int groupId = 4;
		List<CommentView> replies = service.getReply(groupId);
		
		model.addAttribute("replies", replies);
		
		return "comment/reply";
		
	}
}
