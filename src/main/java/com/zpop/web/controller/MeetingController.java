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

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.MeetingRegisterDto;
import com.zpop.web.entity.Category;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.Region;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.service.MeetingService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/meeting")
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
		service.register(dto.toEntity()); // 모임 등록
		
		
		System.out.println("요청받았음");
		// 변수에 담아서 성공 실패만 리턴해줘도 됨.
		return "성공";
	}

	@GetMapping("/{id}")
	public String detailView(@PathVariable int id, Model model, HttpSession session) {
		//쿠키 클라이언트에 저장됨. 보안에 취약함.
		//Cookie cookie = new Cookie();
		//cookie.setAttribute(null, null);
		//단점 너무 많이쓰면 안됨. 
			
		
		
		
		MeetingDetailDto dto = service.getById(id);
		Member member = (Member)session.getAttribute("member");
		List<MeetingParticipantsDto> participants = service.getParticipants(id);
		if(member != null) {
			if(member.getId() == dto.getRegMemberId()) {
				model.addAttribute("memberType","host");
			}
			else if(isMemberParticipated (member,participants)){
				model.addAttribute("memberType" ,"participant");
			}
			else {
				model.addAttribute("memberType" ,"member");
			}
				
		}
		model.addAttribute("dto", dto);
		model.addAttribute("participants", participants);
		model.addAttribute("meetingId", id); 
		model.addAttribute("memberId", 1);//임시로 하드코딩
		
		service.updateViewCount(id); // 조회수 증가 

		System.out.println(model.toString());
		
		return "meeting/detail";
	}
	
	public static boolean isMemberParticipated(Member member, List<MeetingParticipantsDto> participants) {
		
		for(MeetingParticipantsDto dto : participants) {
			System.out.println(dto.getMemberId());
			System.out.println(member.getId());
			if(dto.getMemberId() == member.getId()) {
				return true;
			}
		}
		return false;
	}
	
	
	@PostMapping("/participate")
	@ResponseBody
	public String participate(@RequestBody Participation participation) {
		//String userId = user.getmemberId();
		service.participate(participation);
		
		return "redirect:participate";
	}
	
	@GetMapping("/participate")
	@ResponseBody
	public List<MeetingParticipantsDto> getParticipants(int meetingId) {
		
		return service.getParticipants(meetingId);
	}
	
	
	
	
	}

