package com.zpop.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.RegisterMeetingRequest;
import com.zpop.web.dto.RegisterMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.MeetingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	@Autowired
	private MeetingService service;
	@Autowired
	private CommentService commentService;
	
	@GetMapping("/register")
	public String registerView(Model model) {

		RegisterMeetingResponse response = service.getActiveOptions();
		// TODO: 인가처리
		model.addAttribute("response", response);
		return "/meeting/register";
	}

	@PostMapping("/register")
	@ResponseBody
	public String register(int categoryId, int regionId, int ageRangeId,
			 int contactTypeId,	 int genderCategory, String title, String content,
			 String detailRegion,int maxMember, @DateTimeFormat(pattern= "yyyy-MM-dd'T'HH:mm") LocalDateTime startedAt, String contact, @Nullable List <MultipartFile> images, 
			 @AuthenticationPrincipal ZpopUserDetails userDetails,
			 HttpServletRequest request) throws FileNotFoundException, IOException {
		
		// 저장되는 파일 경로를 controller에서 얻어서 service로 넘겨줌
		String path = "/images";
		String realPath = request.getServletContext().getRealPath(path);
		   
		
		int regMemberId = userDetails.getId();
		System.out.println(regMemberId);
		RegisterMeetingRequest dto = new RegisterMeetingRequest(
				regMemberId,
				categoryId,
				regionId,
				ageRangeId,
				contactTypeId,
				genderCategory,
				title,
				content,
				detailRegion,
				maxMember,
				// LocalDateTime => Date로 변환
				Date.from(startedAt.atZone(ZoneId.systemDefault()).toInstant()),
				contact
			);
		
		dto.toString();
		dto.setRegMemberId(regMemberId);
				
		int meetingId = service.register(dto, images, realPath); // 모임 등록

		// 변수에 담아서 성공 실패만 리턴해줘도 됨.
		return "meetingId: " + regMemberId + "으로 게시글이 등록되었음"; //TODO: 등록이된 모임 id를 넘겨줘야 함. 
		
		
						// 예외
					   //TODO: 1. DB테이블에 Not Null인 column을 입력하지 않았을 경우
					   //	   2. 날짜/시간을 현재 시간보다 과거로 입력했을 때.
	}

	@GetMapping("/{id}")
	public String detailView(@PathVariable int id, Model model, 
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		MeetingDetailDto dto = service.getById(id);
		List<MeetingParticipantsDto> participants = service.getParticipants(id);
		
		int memberId = 0;
		int userType = 0;
		if(userDetails!=null) {
			memberId = userDetails.getId();
			userType = service.getUserType(memberId,id);
		}
		
		model.addAttribute("dto", dto);
		model.addAttribute("participants", participants);
		model.addAttribute("meetingId", id); 
		model.addAttribute("userType",userType);
		// service의 public 메서드 -> 사용자가 쓰는 기능
		// 따라서 private updateViewCount으로 바꾸고 getById안에 넣기.
		service.updateViewCount(id); // 조회수 증가 
		
		/*------------------------ 댓글 부분 ---------------------------*/
		List<CommentView> comments = null;
		if(memberId != 0)
			comments = commentService.getCommentWithWriter(memberId, id);
		else 
			comments = commentService.getComment(id);
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
	
	//참여 AJAX endpoint (js에서 콜하는 함수)
	@PostMapping("/participate/{meetingId}")
	@ResponseBody
	public String participate(@PathVariable int meetingId, 
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		
		int memberId = userDetails.getId();
		service.participate(meetingId, memberId);
		
		return "meeting/detail";
	}
}
