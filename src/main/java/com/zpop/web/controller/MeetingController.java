package com.zpop.web.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zpop.web.dto.*;
import com.zpop.web.entity.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.CommentService;
import com.zpop.web.service.MeetingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/meeting")
public class MeetingController {
	@Autowired
	private MeetingService service;

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
			@AuthenticationPrincipal ZpopUserDetails userDetails, HttpServletRequest request)
			throws FileNotFoundException, IOException {

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
		return ResponseEntity.ok(response); // TODO: 등록이된 모임 id를 넘겨줘야 함.

		// 예외
		// TODO: 1. DB테이블에 Not Null인 column을 입력하지 않았을 경우
		// 2. 날짜/시간을 현재 시간보다 과거로 입력했을 때.
	}

	@GetMapping("/update/{id}")
	public String getMeetingUpdateView(@PathVariable int id, Model model,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		/*
		 * if (userDetails.getId() != id) { throw new
		 * ResponseStatusException(HttpStatus.NOT_FOUND); }
		 */
		RegisterMeetingViewResponse response = service.getActiveOptions();
		model.addAttribute("response", response);
		UpdateMeetingViewDto dto = service.getUpdateMeetingView(id);
		System.out.println(dto.toString());
		model.addAttribute("meetingDetail", dto);

		return "/meeting/update";
	}

	@GetMapping("/{id}")
	public String detailView(@PathVariable int id, Model model, @AuthenticationPrincipal ZpopUserDetails userDetails) {
		Integer memberId = null;

		if (userDetails != null)
			memberId = userDetails.getId();

		MeetingDetailResponse meetingDetail = service.getById(id, memberId);

		model.addAttribute("meeting", meetingDetail);

		return "meeting/detail";
	}

	@PutMapping("/update/{id}")
	@ResponseBody()
	public ResponseEntity<?> updateMeeting(@PathVariable("id") int meetingId,
			@Valid @RequestBody UpdateMeetingRequest dto, @AuthenticationPrincipal ZpopUserDetails userDetails,
			HttpServletRequest request) throws IOException {
		int memberId = userDetails.getId();
		dto.setRegMemberId(memberId);
		dto.setMeetingId(meetingId);
		boolean isMeetingUpdated = service.updateMeeting(dto);

		if (isMeetingUpdated) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	// 참여자목록 AJAX endpoint(js가 콜하는 함수)
	@GetMapping("{meetingId}/participant")
	@ResponseBody
	public List<ParticipantResponse> getParticipant(@PathVariable int meetingId) {
		List<ParticipantResponse> list = service.getParticipants(meetingId);
		return list;
	}

	// 참여 AJAX endpoint (js에서 콜하는 함수)
	@PostMapping("/{meetingId}/participate")
	@ResponseBody
	public Map<String, Object> participate(@PathVariable int meetingId,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		// TODO: 임시로 MAP 리턴함
		int memberId = userDetails.getId();
		service.participate(meetingId, memberId);

		Map<String, Object> result = new HashMap<>();
		result.put("success", true);

		return result;
	}

	@GetMapping("/{id}/contact")
	@ResponseBody
	public String getContact(@PathVariable int id, @AuthenticationPrincipal ZpopUserDetails userDetails) {

		if (userDetails == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		String contact = service.getContact(id, userDetails.getId());

		return contact;
	}

	@GetMapping("/list")
	public String getListView(Model model) {
		List<MeetingThumbnailResponse> meetings = service.getList();

		model.addAttribute("meetings", meetings);

		return "meeting/list";
	}

	@GetMapping("/api/list")
	@ResponseBody
	public List<MeetingThumbnailResponse> getList(@RequestParam(required = false, defaultValue = "0") int start,
			@RequestParam(required = false) String keyword, @RequestParam(required = false) Integer category,
			@RequestParam(required = false) String regions, @RequestParam(required = false) Boolean isClosed) {
		List<MeetingThumbnailResponse> meetings = service.getList(start, keyword, category, regions, isClosed);

		return meetings;
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	public boolean delete(@PathVariable(name = "id") int id, @AuthenticationPrincipal ZpopUserDetails userDetails) {
		int memberId = userDetails.getId();
		boolean result = service.delete(id, memberId);
		return result;
	}

	@DeleteMapping("/{id}/participant/{participantId}")
	@ResponseBody
	public boolean kick(@PathVariable(name = "id") int id, @PathVariable(name = "participantId") int participantId , 
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		int memberId = userDetails.getId();
		boolean result = service.kick(id, participantId, memberId);
		return result;
	}

	@PatchMapping("/close/{id}")
	@ResponseBody
	public boolean close(@PathVariable(name = "id") int id , @AuthenticationPrincipal ZpopUserDetails userDetails) {
		int memberId = userDetails.getId();
		return service.close(id, memberId);
	}

}
