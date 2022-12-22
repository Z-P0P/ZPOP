package com.zpop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.service.CommentService;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	@Autowired
	CommentService service;
	
	@GetMapping("/register")
	public String registerView() {
		// TODO: 인가처리
		return "meeting/register";
	}

	@GetMapping("/{id}")
	public String detailView(@PathVariable int id, Model model) {
		
		List<CommentView> comments = service.getComment(id);
		int countOfComment = service.getCountOfComment(id);
		System.out.println(comments);
		model.addAttribute("meetingId", id);
		model.addAttribute("comments", comments);
		model.addAttribute("countOfComment", countOfComment);
		
		
		
		return "meeting/detail2";
	}
	@PostMapping("comment")
	@ResponseBody
	public  String registerComment(@RequestBody Comment comment) {
		service.registerComment(comment);
		return "";
	}
	
	@PostMapping("reply")
	public  String getReply(@RequestBody Comment comment, Model model) {
		List<CommentView> replies = service.getReply(comment.getParentCommentId());
		model.addAttribute("replies", replies);
		return "meeting/detail2";
	}
}