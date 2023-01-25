package com.zpop.web.controller;

import com.zpop.web.dto.*;
import com.zpop.web.entity.Member;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.MeetingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/meeting")
@RequiredArgsConstructor
public class MeetingController {

	private final MeetingService service;
	
	@GetMapping("/register")
	public ResponseEntity<?> getRegisterOptions(Model model) {

		RegisterMeetingViewResponse response = service.getActiveOptions();
		// TODO: 인가처리

		return ResponseEntity.ok(response);
	}
	
	private final String PATH = "/images";

	@PostMapping()
	@ResponseBody
	public ResponseEntity<RegisterMeetingResponse> postMeeting(@Valid @RequestBody RegisterMeetingRequest dto, 
			 @AuthenticationPrincipal ZpopUserDetails userDetails,
			 HttpServletRequest request) throws FileNotFoundException, IOException {
		
		// 저장되는 파일 경로를 controller에서 얻어서 service로 넘겨줌
		
		int regMemberId = userDetails.getId();
		dto.setRegMemberId(regMemberId);
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

	@GetMapping("/list")
	public ResponseEntity<List<MeetingThumbnailResponse>>  getList(
			@RequestParam(required = false, defaultValue = "0") int start,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) Integer category,
			@RequestParam(required = false) String regions,
			@RequestParam(required = false) Boolean isClosed
	){
		List<MeetingThumbnailResponse> list = service.getList(start, keyword, category, regions, isClosed);

		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/update/{id}")
	@ResponseBody
	public Map<String, Object> getMeetingUpdateView(@PathVariable int id
								,@AuthenticationPrincipal ZpopUserDetails userDetails) {
	/*	if (userDetails.getId() != id) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}*/
		RegisterMeetingViewResponse activeOptions = service.getActiveOptions();
		UpdateMeetingViewDto meetingDetails = service.getUpdateMeetingView(id);
		Map<String, Object> response = new HashMap<>();
		response.put("options", activeOptions);
		response.put("details", meetingDetails);


		return response;
	}


	
	@GetMapping("/{id}")
	public ResponseEntity<MeetingDetailResponse> getDetail(@PathVariable int id, 
											@AuthenticationPrincipal ZpopUserDetails userDetails)
							 {

		Integer memberId = null;
		
		
		if(userDetails != null)
			memberId = userDetails.getId();

		MeetingDetailResponse meetingDetail = service.getById(id, memberId);

		return ResponseEntity.ok().body(meetingDetail);
	}
	
	@PutMapping("{id}")
	@ResponseBody()
	public ResponseEntity<Object> updateMeeting(@PathVariable("id") int meetingId
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

	//참여자목록 AJAX endpoint(js가 콜하는 함수)
	@GetMapping("{id}/participant")
	public List<ParticipantResponse> getParticipant(@PathVariable int id){
		List<ParticipantResponse> list = service.getParticipants(id);
		return list;
	}
	
	//참여 AJAX endpoint (js에서 콜하는 함수)
	@PostMapping("/{id}/participate")
	public ResponseEntity<ParticipationResponse> participate(@PathVariable int id,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		int memberId = userDetails.getId();
		ParticipationResponse res = service.participate(id, memberId);
		return ResponseEntity.ok(res);

	}
	//참여취소 AJAX endpoint(js에서 콜하는 함수)
	@DeleteMapping("/{meetingId}/leave")
	public boolean cancelParticipate(@PathVariable int meetingId,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		int memberId = userDetails.getId();
		boolean result = service.cancelParticipate(meetingId, memberId);
		return result;
		
	}
	@GetMapping("/{id}/contact")
	@ResponseBody
	public String getContact(@PathVariable int id,
		    @AuthenticationPrincipal ZpopUserDetails userDetails) {

		if(userDetails == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		String contact = service.getContact(id, userDetails.getId());

		return contact;
	}
	
	@PatchMapping("/{id}/close")
	public boolean closeMeeting(@PathVariable int id,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		Member member = new Member();
		member.setId(userDetails.getId());
		boolean result = service.close(id, member);
		return result;
		
	}
	
	
}
