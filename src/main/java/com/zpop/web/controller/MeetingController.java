package com.zpop.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.MeetingRegisterDto;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.MeetingService;
import com.zpop.web.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	@Autowired
	private MeetingService service;
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemberService memberService;
	@GetMapping("/register")
	public String registerView() {

		// TODO: 인가처리
		return "/meeting/register";
	}
	
	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestBody MeetingRegisterDto dto) {

		service.register(dto.toEntity()); // 모임 등록

		// 변수에 담아서 성공 실패만 리턴해줘도 됨.
		return "성공"; //TODO: 등록이된 모임 id를 넘겨줘야 함. 
		
		
						// 예외
					   //TODO: 1. DB테이블에 Not Null인 column을 입력하지 않았을 경우
					   //	   2. 날짜/시간을 현재 시간보다 과거로 입력했을 때.
	}

	@GetMapping("/{id}")
	public String detailView(@PathVariable int id, Model model, HttpSession session) {
		// TODO: getById에 Member or memberId로 넣어서 밑에 비즈니스로직 service 레이어로 옮기기
		
		MeetingDetailDto dto = service.getById(id);
		List<MeetingParticipantsDto> participants = service.getParticipants(id);
		Member member = (Member)session.getAttribute("member");
		
		int userType = 0;
		int commenterType = 0;
		if(member != null) {
			int memberId = member.getId();
			userType = service.getUserType(memberId,id);
			commenterType = commentService.getCommenterType(memberId, id);
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("participants", participants);
		model.addAttribute("meetingId", id); 
		model.addAttribute("userType",userType);
		//session.setAttribute("memberId", member.getId());
		// service의 public 메서드 -> 사용자가 쓰는 기능
		// 따라서 private updateViewCount으로 바꾸고 getById안에 넣기.
		service.updateViewCount(id); // 조회수 증가 
		
		/*------------------------ 댓글 부분 ---------------------------*/
		
		List<CommentView> comments = commentService.getComment(id);
		int countOfComment = commentService.getCountOfComment(id);
		model.addAttribute("comments", comments);
		model.addAttribute("countOfComment", countOfComment);
		return "meeting/detail";
	}
	
	//참여자목록 AJAX endpoint(js가 콜하는 함수)
	@GetMapping("{meetingId}/participant")
	@ResponseBody
	public List<MeetingParticipantsDto> getParticipant(@PathVariable int meetingId){
		List<MeetingParticipantsDto> list = service.getParticipants(meetingId);
		return list;
	}
	
	// TODO: service 레이어로
	
	//댓글 AJAX endpoint (js에서 콜하는 함수)
	@GetMapping("{meetingId}/comment")
	@ResponseBody
	public  Map<String, Object> getComment(@PathVariable int meetingId) {
		
		List<CommentView> comments = commentService.getComment(meetingId);
		int countOfComment = commentService.getCountOfComment(meetingId);
		
		Map<String,Object> dto = new HashMap<>();
		dto.put("status",200);
		dto.put("resultObject",comments);
		dto.put("countOfComment",countOfComment);
		
		return dto;
	}
	

	// 예외처리 리스트
	
//		1. 참여하려는 사용자가 주최자인 경우 --> memberId == regmemberId
//		2. 해당 모임에 대해서 강퇴된 사용자가 참여하기 버튼을 누를 경우  
//		3. 로그인을 하지 않은 사용자가 참여하기 버튼을 누른 경우 -> 로그인 모달이 나와야됨.
//		4. 내가 이미 참여한 모임일 경우
	
	//참여 AJAX endpoint (js에서 콜하는 함수)
	@PostMapping("/participate/{meetingId}")
	@ResponseBody
	public String participate(@PathVariable int meetingId, HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		int memberId = member.getId();
		service.participate(meetingId, memberId);
		
		return "meeting/detail";
	}
}

