package com.zpop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zpop.web.entity.Comment;
import com.zpop.web.entity.CommentView;
import com.zpop.web.service.CommentService;

@Controller
//@RequestMapping("/")
public class CommentController {
	@Autowired
	CommentService service;
	
	@GetMapping("/comment")
	public String comment(Model model) {
		int meetingId = 1;
		List<CommentView> comments = service.getComment(meetingId);
		int countOfComment = service.getCountOfComment(meetingId);
		
		model.addAttribute("comments", comments);
		model.addAttribute("countOfComment", countOfComment);
		
		return "comment/comment";
		
	}
	@GetMapping("/comment2")
	public String comment2(Model model) {
		int meetingId = 1;
		List<CommentView> comments = service.getComment(meetingId);
		int countOfComment = service.getCountOfComment(meetingId);
		
		model.addAttribute("comments", comments);
		model.addAttribute("countOfComment", countOfComment);
		
		return "comment/comment2";
		
	}
	@GetMapping("/reply")
	public String reply(Model model) {
		int groupId = 4;
		List<CommentView> replies = service.getReply(groupId);
		
		model.addAttribute("replies", replies);
		
		return "comment/reply";
	}
	
	@GetMapping("comment-p")
	public Comment showComment(@RequestParam Comment comment) {
		return comment;
	}
	
	@PostMapping("/comment")
	public  String registerComment(@RequestBody Comment comment) {
		return "";
	}
}
