package com.zpop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	
	@GetMapping("/register")
	public String registerView() {
		// TODO: 인가처리
		return "meeting/register";
	}

	@GetMapping("/{id}")
	public String detailView() { // Model model
		// 개발 테스트용 
		// model.addAttribute("tempMember", 1);
		// model.addAttribute("isMine", false);

		// return "meeting/detail-imple";
		return "meeting/detail";
	}
}