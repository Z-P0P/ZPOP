package com.zpop.web.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.MeetingRegisterDto;
import com.zpop.web.service.MeetingService;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	// controller -> 요청하는 페이지가 있음

	@Autowired
	private MeetingService service;
	@GetMapping("/register")
	public String registerView(Model model) {

		// TODO: 인가처리
		return "meeting/register";
	}

	
	@PostMapping("/register")
	@ResponseBody
	public String register1(@RequestBody MeetingRegisterDto dto) {
		System.out.println(dto.toString());
		//db에 저장을하려면 컨트롤러,
		//서비스는 entity  dao
		service.register(dto.toEntity());
		
		return "redirect:register";
	}

	@GetMapping("/{id}")
	public String detailView() {
		return "meeting/detail";
	}
	
	
	@PostMapping("/test")
	@ResponseBody
	public boolean participate(@RequestBody HashMap<String, Object> obj) {
		// testMemberId는 임의로 값 설정 세션 OR 인가
		
		int meetingId = (Integer)obj.get("meetingId");
		int testMemberId =  (Integer)obj.get("testMemberId");
		
		System.out.printf("meetingid =  %d testMemberId = %d",meetingId,testMemberId);
		boolean isSucceed = service.participate(meetingId,testMemberId);
		System.out.println(isSucceed);
		return isSucceed;
		
	}
}

