package com.zpop.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.MeetingRegisterDto;
import com.zpop.web.entity.Participation;
import com.zpop.web.service.MeetingService;

import groovy.util.logging.Slf4j;

@Controller
@RequestMapping("/meeting")
@Slf4j
public class MeetingController {
	// controller -> 요청하는 페이지가 있음

	@Autowired
	private MeetingService service;
	
	@GetMapping("/register")
	public String registerView() {

		// TODO: 인가처리
		return "/meeting/register";
	}
// 
	
	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestBody MeetingRegisterDto dto) {
//		System.out.println(dto.toString());
		//db에 저장을하려면 컨트롤러,
		//서비스는 entity  dao
		System.out.println("dto.StartedAt = " + dto.getStartedAt());
		service.register(dto.toEntity());
		
		System.out.println("요청받았음");
		// 변수에 담아서 성공 실패만 리턴해줘도 됨.
		return "성공";
	}

	@GetMapping("/{id}")
	public String detailView() {
		return "meeting/detail";
	}
	
	
	
	@PostMapping("/participate")
	@ResponseBody
	public String participate(@RequestBody Participation participation) {
		//String userId = user.getmemberId();
		service.participate(participation);
		
		return "redirect:participate";
		
	
//	public boolean participate(@RequestBody HashMap<String, Object> obj) {
//		// testMemberId는 임의로 값 설정 세션 OR 인가
//		
//		int meetingId = (Integer)obj.get("meetingId");
//		int testMemberId =  (Integer)obj.get("testMemberId");
//		
//		System.out.printf("meetingid =  %d testMemberId = %d",meetingId,testMemberId);
//		boolean isSucceed = service.participate(meetingId,testMemberId);
//		System.out.println(isSucceed);
//		return isSucceed;
		
	}
}

