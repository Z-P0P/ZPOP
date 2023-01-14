package com.zpop.web.controller;

import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.MeetingService;
import com.zpop.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    private MeetingService meetingService;

	//CORS ?

		@GetMapping("/member")
	public ResponseEntity<Member> getMyPage( @AuthenticationPrincipal ZpopUserDetails userDetails) {
			Member member = service.getById(userDetails.getId());
			return ResponseEntity.ok().body(member);
	}

	@GetMapping("/myMeeting")
	public ResponseEntity<List<MyMeetingResponse>> getMyMeeting(@AuthenticationPrincipal ZpopUserDetails userDetails){
		List<MyMeetingResponse> meetings = service.getMyMeeting(userDetails.getId());
		Member member = service.getById(userDetails.getId());
		return ResponseEntity.ok().body(meetings);
	}
//	public String getMeeting(@AuthenticationPrincipal ZpopUserDetails userDetails, Model model) {
//		List<MyMeetingResponse> meetings = service.getMyMeeting(userDetails.getId());
//		Member member = service.getById(userDetails.getId());
//		model.addAttribute("member", member);
//		model.addAttribute("meetings", meetings);
//        return "member/my-meeting";
//    }


//	// 마이페이지 진입
//	@GetMapping("/me")
//	public String getMyPage(@AuthenticationPrincipal ZpopUserDetails userDetails,
//							 Model model) {
//		Member member = service.getById(userDetails.getId());
//		model.addAttribute("member", member);
//		return "member/my-profile";
//	}
//
//	@GetMapping("me/edit")
//	public String getEditPage(@AuthenticationPrincipal ZpopUserDetails userDetails, Model model) {
//		Member member = service.getById(userDetails.getId());
//		model.addAttribute("member", member);
//		return "member/profile-edit";
//	}
//
//	@GetMapping("/me/meeting")
//	public String getMeeting(@AuthenticationPrincipal ZpopUserDetails userDetails, Model model) {
//		List<MyMeetingResponse> meetings = service.getMyMeeting(userDetails.getId());
//		Member member = service.getById(userDetails.getId());
//		model.addAttribute("member", member);
//		model.addAttribute("meetings", meetings);
//        return "member/my-meeting";
//    }
//
//    @ResponseBody
//    @GetMapping("/eval/{dataId}")
//    public List<EvalMemberDto> getParticipants(@PathVariable("dataId") int meetingId) {
//		List<EvalMemberDto> participant = service.getEvalMember(meetingId) ;
//        return participant;
//    }
//
//	@ResponseBody
//	@PostMapping("/rate")
//	public void rateMember(@RequestBody EvalDto dto, @AuthenticationPrincipal ZpopUserDetails userDetails) {
//		if (userDetails == null){
//			//로그인 요청
//		}
//		int evaluatorId = userDetails.getId();
//		dto.setEvaluatorId(evaluatorId);
//		service.getRateData(dto);
//	}
//
//
//	//내가모집한 모임 조회
//	@GetMapping("/me/gathering")
//	public String getGathering(@AuthenticationPrincipal ZpopUserDetails userDetails, Model model) {
//		List<MyMeetingResponse> meetings = service.getMyGathering(userDetails.getId());
//		model.addAttribute("meetings", meetings);
//		return "member/my-gathering";
//
//	}
}
