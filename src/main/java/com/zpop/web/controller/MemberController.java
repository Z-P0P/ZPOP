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
import java.util.Map;

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

@PostMapping("/nickname/validation")
@ResponseBody
public ResponseEntity<?> validateNickname(String nickname) {
	System.out.println(nickname);
	Map<String, Object> response = service.checkNicknameValid(nickname);
	return ResponseEntity.ok(response);
}
@ResponseBody
@PostMapping("/me/edit")
public int setNickname(@AuthenticationPrincipal ZpopUserDetails userDetails ,String nickname){
		// 만약 userDetails == null  로그인 요청
		// 그게아니면
		
		int memberId = userDetails.getId();
		service.updateNickname(memberId, nickname);
		
		

		// 멤버 닉네임 로그 뒤져서 30일 내에 변경기록이 없는지 확인

		// SELECT 조건 
		// 만약 있다면 -> 클라이언트에게 변경할 수 없다고 응답
		
		// 만약 없다면 ->  닉네임 중복 검사 해주기 -> 중복이면 중복이라고 응답
		   //중복도 아니라면
		// 멤버의 닉네임을 업데이트하는 쿼리를 실행해준다.

		
		return 1;
}

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
		System.out.println(participant);
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
