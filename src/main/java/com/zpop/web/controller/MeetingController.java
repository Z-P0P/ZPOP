package com.zpop.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.RegisterMeetingRequest;
import com.zpop.web.dto.RegisterMeetingResponse;
import com.zpop.web.dto.RegisterMeetingViewResponse;
import com.zpop.web.dto.UpdateMeetingRequest;
import com.zpop.web.dto.UpdateMeetingViewDto;
import com.zpop.web.entity.MeetingFile;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.MeetingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

/*
 * 작성자: 임형미 & 
 */
@Controller
@RequestMapping("/meeting")
public class MeetingController {

	@Autowired
	private MeetingService service;

	@Autowired
	CommentService commentService;

	@GetMapping("/register")
	public String registerView(Model model) {

		RegisterMeetingViewResponse response = service.getActiveOptions();
		// TODO: 인가처리
		model.addAttribute("response", response);
		return "/meeting/register";
	}
	
	private final String PATH = "/images";

	@PostMapping("/register")
	@ResponseBody
	public ResponseEntity<RegisterMeetingResponse> register(@Valid @RequestBody RegisterMeetingRequest dto, 
			 @AuthenticationPrincipal ZpopUserDetails userDetails,
			 HttpServletRequest request) throws FileNotFoundException, IOException {
		
		// 저장되는 파일 경로를 controller에서 얻어서 service로 넘겨줌
		
		int regMemberId = userDetails.getId();
		dto.setRegMemberId(regMemberId);
		System.out.println(dto.toString());
		RegisterMeetingResponse response = null;
		// 나중에는 @ControllerAdvice 로 바꾸기
		try {
			response = service.register(dto); // 모임 등록			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(response);
		}

		// 변수에 담아서 성공 실패만 리턴해줘도 됨.
		return ResponseEntity.ok(response); //TODO: 등록이된 모임 id를 넘겨줘야 함. 
		
		
						// 예외
					   //TODO: 1. DB테이블에 Not Null인 column을 입력하지 않았을 경우
					   //	   2. 날짜/시간을 현재 시간보다 과거로 입력했을 때.
	}
	
	@GetMapping("/update/{id}")
	public String getMeetingUpdateView(@PathVariable int id
								,Model model
								,@AuthenticationPrincipal ZpopUserDetails userDetails) {
	/*	if (userDetails.getId() != id) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}*/
		RegisterMeetingViewResponse response = service.getActiveOptions();
		model.addAttribute("response", response);
		UpdateMeetingViewDto dto = service.getUpdateMeetingView(id);
		System.out.println(dto.toString());
		model.addAttribute("meetingDetail", dto);
		
		return "/meeting/update";
	}
	
	@PutMapping("/update/{id}")
	@ResponseBody()
	public ResponseEntity<?> updateMeeting(@PathVariable("id") int meetingId
					, @Valid @RequestBody UpdateMeetingRequest dto
					, @AuthenticationPrincipal ZpopUserDetails userDetails
					, HttpServletRequest request) throws IOException {
		int memberId = userDetails.getId();
		dto.setRegMemberId(memberId);
		dto.setMeetingId(meetingId);
		boolean isMeetingUpdated = service.updateMeeting(dto);

		if (isMeetingUpdated) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{id}")
	public String detailView(@PathVariable int id, Model model, HttpSession session) {
		// TODO: 댓글이랑 합치기
		// TODO: getById에 Member or memberId로 넣어서 밑에 비즈니스로직 service 레이어로 옮기기
		MeetingDetailDto dto = service.getById(id);
		Member member = (Member) session.getAttribute("member");
		List<MeetingParticipantsDto> participants = service.getParticipants(id);
		if (member != null) {
			if (member.getId() == dto.getRegMemberId()) {
				model.addAttribute("memberType", "host");
			} else if (isMemberParticipated(member, participants)) {
				model.addAttribute("memberType", "participant");
			} else {
				model.addAttribute("memberType", "member");
			}
		}
		model.addAttribute("dto", dto);
		model.addAttribute("participants", participants);
		model.addAttribute("meetingId", id); 
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
	
	
	// TODO: service 레이어로
	public static boolean isMemberParticipated(Member member, List<MeetingParticipantsDto> participants) {
		for (MeetingParticipantsDto dto : participants) {
			if (dto.getMemberId() == member.getId()) {
				return true;
			}
		}
		return false;
	}

	
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
	
//	@GetMapping("/participate")
//	@ResponseBody
//	public List<MeetingParticipantsDto> getParticipants(int meetingId) {
//		
//		return service.getParticipants(meetingId);
//	}
	// 예외처리 리스트
	
//		1. 참여하려는 사용자가 주최자인 경우 --> memberId == regmemberId
//		2. 해당 모임에 대해서 강퇴된 사용자가 참여하기 버튼을 누를 경우  
//		3. 로그인을 하지 않은 사용자가 참여하기 버튼을 누른 경우 -> 로그인 모달이 나와야됨.
//		4. 내가 이미 참여한 모임일 경우
	
	@PostMapping("/participate/{meetingId}")
	@ResponseBody
	public String participate(@PathVariable int meetingId, HttpSession session) {
		
		Member member = (Member)session.getAttribute("member");
		int memberId = member.getId();
		service.participate(meetingId, memberId);
		
		return "meeting/detail";
	}
}
