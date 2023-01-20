package com.zpop.web.controller;

import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*****
 * 1. 다른 사용자 프로필 조회 --> 모달 url 불필요 2. 마이 프로필 페이지 진입 (/member/me) --> 페이지 3.
 * /member/me/meeting --> 내가 참여한 모임 (임시) 4. /member/me/gather --> 내가 모집한 모임 (임시)
 * 5. 탈퇴하기 url ?
 *****/

// 1. 헤더에서 마이프로필 클릭 -> 로그인한 유저가 권한이 있는지 확인
// 2. 로그인한 유저가 권한이 있다면 , 마이프로필 페이지로 이동
// 2-2. 권한이 없다면 접근권한이 없습니다! 띄우기
// 3. 마이프로필 페이지로 이동

@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired
	private MemberService service;


	//CORS ?

	@GetMapping("/me")
	public ResponseEntity<Member> getMyPage(@AuthenticationPrincipal ZpopUserDetails userDetails) {
		Member member = service.getById(userDetails.getId());
		return ResponseEntity.ok().body(member);
	}
//	@GetMapping("/me")
//	public ResponseEntity<Member> getMyPage(@AuthenticationPrincipal ZpopUserDetails userDetails) {
//		Member member = service.getById(userDetails.getId());
//		return ResponseEntity.ok().body(member);
//	}

	@GetMapping("/myMeeting")
	public ResponseEntity<List<MyMeetingResponse>> getMeeting(@AuthenticationPrincipal ZpopUserDetails userDetails){
		List<MyMeetingResponse> meetings = service.getMyMeeting(userDetails.getId());
		return ResponseEntity.ok().body(meetings);
	}
	@GetMapping("/myGathering")
	public ResponseEntity<List<MyMeetingResponse>> getGathering(@AuthenticationPrincipal ZpopUserDetails userDetails){
		List<MyMeetingResponse> meetings = service.getMyGathering(userDetails.getId());
		//추후수정
		if(meetings.isEmpty()) {
			return  new ResponseEntity<>(null, HttpStatus.OK);
		}
		return ResponseEntity.ok().body(meetings);
	}

	@ResponseBody
	@GetMapping("/partList/{meetingId}")
	public ResponseEntity<List<EvalMemberDto>> getParticipants(@PathVariable("meetingId") int meetingId) {
		List<EvalMemberDto> participant = service.getEvalMember(meetingId) ;
//        return participant;
		return ResponseEntity.ok().body(participant);
	}

	@PostMapping("/rate")
	public ResponseEntity<EvalDto> rateMember(@RequestBody EvalDto dto, @AuthenticationPrincipal ZpopUserDetails userDetails) {
		if (userDetails == null){
			//로그인 요청
		}
		int evaluatorId = userDetails.getId();
		dto.setEvaluatorId(evaluatorId);
		service.getRateData(dto);

		return ResponseEntity.ok().body(dto);
	}

}
