package com.zpop.web.controller;

import com.zpop.web.dto.EvalDto;
import com.zpop.web.dto.EvalMemberDto;
import com.zpop.web.dto.MyMeetingResponse;
import com.zpop.web.dto.ProfileResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.ProfileFile;
import com.zpop.web.security.UserSecurityService;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired
	private MemberService service;
	@Autowired
	private UserSecurityService userSecurityService;



/**
 * 마이 프로필 페이지
 * @param userDetails
 * @return
 */
@GetMapping("/me")
public ResponseEntity<Member> getMyPage(@AuthenticationPrincipal ZpopUserDetails userDetails) {
	Member member = service.getById(userDetails.getId());
	return ResponseEntity.ok().body(member);
}


/**
 * 닉네임 변경
 * @param userDetails
 * @param nickname
 * @return
 * 1.멤버 닉네임 로그 뒤져서 30일 내에 변경기록이 없는지 확인
 * 2.SELECT 조건 
 * 2-1 변경기록이 있다면 -> 클라이언트에게 변경할 수 없다고 응답
 * 2-2 변경기록이 없다면 -> 닉네임 중복검사 -> 중복응답 
 *  				                      ->중복이 아니면, 멤버 닉네임 업데이트 쿼리 실행
 */
@PostMapping("/me/edit")
public ResponseEntity<?> update(@AuthenticationPrincipal ZpopUserDetails userDetails,
									 String nickname,
									 String imageName,
									 HttpSession session){
		int memberId = userDetails.getId();
		Member updatedMember = service.update(memberId, nickname, imageName);
		UserDetails user = userSecurityService.loadUserByUsername(updatedMember.getNickname());
		Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		return ResponseEntity.ok(200);
	}

/**
 * 닉네임 유효성검사
 * @param nickname
 * @return
 * 
 */
@PostMapping("/nickname/validation")
public ResponseEntity<?> validateNickname(String nickname) {
	System.out.println(nickname);
	Map<String, Object> response = service.checkNicknameValid(nickname);
	return ResponseEntity.ok(response);
}


/**
 * 내가 참여한 모임
 * @param userDetails
 * @return
 */
@GetMapping("/myMeeting")
public ResponseEntity<List<MyMeetingResponse>> getMeeting(@AuthenticationPrincipal ZpopUserDetails userDetails){
	List<MyMeetingResponse> meetings = service.getMyMeeting(userDetails.getId());
	return ResponseEntity.ok().body(meetings);
}

/**
 * 내가 모집한 모임
 * @param userDetails
 * @return
 */
@GetMapping("/myGathering")
public ResponseEntity<List<MyMeetingResponse>> getGathering(@AuthenticationPrincipal ZpopUserDetails userDetails){
	List<MyMeetingResponse> meetings = service.getMyGathering(userDetails.getId());
	//추후수정
	if(meetings.isEmpty()) {
		return  new ResponseEntity<>(null, HttpStatus.OK);
	}
	return ResponseEntity.ok().body(meetings);
}

/**
 * 내가 참여한 모임 -> 평가하기 클릭시, 해당 모임에 참여한 참여자 리스트
 * @param meetingId
 * @return
 */
@GetMapping("/partList/{meetingId}")
public ResponseEntity<List<EvalMemberDto>> getParticipants(@PathVariable("meetingId") int meetingId) {
	List<EvalMemberDto> participant = service.getEvalMember(meetingId) ;
	System.out.println(participant);
	return ResponseEntity.ok().body(participant);
}

/**
 * 내가 참여한 모임 -> 평가하기
 * @param dto
 * @param userDetails
 * @return
 */
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

@GetMapping("/member/{id}")
public ResponseEntity<ProfileResponse> getProfile(@PathVariable("id") int id){
	ProfileResponse profile = service.getProfile(id);
	return ResponseEntity.ok(profile);
}



@PostMapping("/upload/profile")
@ResponseBody //ㅠㅠ
public ResponseEntity<ProfileFile> uploadFile(@NotNull MultipartFile file
		, @NotNull @NotEmpty String path
		, HttpServletRequest request
		, @AuthenticationPrincipal ZpopUserDetails userDetails) throws IOException {
	String realPath = request.getServletContext().getRealPath(path);
	System.out.println(realPath);

	int memberId = userDetails.getId();
	System.out.println(userDetails);
	ProfileFile profileFile = service.uploadFile(file, realPath, memberId);
	return ResponseEntity.ok(profileFile);
}

}
