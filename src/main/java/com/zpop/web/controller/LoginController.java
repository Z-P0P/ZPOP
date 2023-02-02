package com.zpop.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.NotificationDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dao.SocialTypeDao;
import com.zpop.web.dto.BlockedMemberDto;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.global.exception.CustomException;
import com.zpop.web.global.exception.ExceptionReason;
import com.zpop.web.security.UserSecurityService;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/login")
public class LoginController {

	private LoginService loginService;
	private Map<String, LoginService> loginServiceMap;
	private UserSecurityService userSecurityService;
	
	@Autowired
	private ParticipationDao participationDao;
	
	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	public LoginController(MemberDao memberDao, 
			Map<String, LoginService> loginServiceMap
			, SocialTypeDao socialTypeDao
			, UserSecurityService userSecurityService) {
		this.loginServiceMap = loginServiceMap;
		this.userSecurityService = userSecurityService;
	}

	// 소셜 로그인 시, kakao, naver를 공통적으로 처리함
	@GetMapping("/oauth/{loginType}")
	@ResponseBody
	public ResponseEntity<?> OAuthlogin(@PathVariable("loginType") String loginType
			,@RequestParam @Nullable String code 
			, @RequestParam @Nullable String state
			, HttpSession session
			, @AuthenticationPrincipal ZpopUserDetails userDetails
	)
			throws IOException, InterruptedException {
		Map<String, Object> result = new HashMap<>();

		//이미 로그인되어 있는 경우 그냥 사용자 정보를 전달
		if (userDetails != null) {
			result.put("code", "success");
			result.put("id", userDetails.getId());
			result.put("nickname", userDetails.getUsername());
			result.put("profileImagePath", userDetails.getProfileImagePath());
			result.put("fame", userDetails.getFame());
	
			return ResponseEntity.ok(result);
		}
		
		loginService = loginServiceMap.get(loginType+"LoginService");
		String accessToken = (loginService.getAccessToken(code,state));
		String socialId = loginService.getSocialId(accessToken);
		Member member = loginService.getMemberInfo(socialId);

		if (member == null) {
			session.setAttribute("socialId", socialId);
			session.setAttribute("loginType", loginType);
			result.put("code", "REGISTER_REQUIRED");
			return ResponseEntity.ok(result);
		}

		/*
		 * 사용자가 차단된 기록이 있는지 확인
		 */

		BlockedMemberDto blockedMember= loginService.getBlockedMemberById(member.getId());

		// 만약 db에 차단 기록이 있다면
		if (blockedMember != null){
			Date currentDate = new Date();
			// 차단종료일자(releasedAt) 이 현재 날짜보다 이후라면 예외 처리 (차단된 사용자)
			if (blockedMember.getReleasedAt().after(currentDate)){
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy 년 MM 월 dd 일 HH 시 mm 분");
				List<String> details = new ArrayList<>();
				// 예외에는 사유를 전달 (신고사유, 차단기간)
				String blockedAt = dateFormat.format(blockedMember.getBlockedAt());
				String releasedAt = dateFormat.format(blockedMember.getReleasedAt());
				details.add("신고사유 : " + blockedMember.getReportedType());
				details.add("차단기간 : " + blockedAt + " ~ " + releasedAt);
				throw new CustomException(ExceptionReason.BLOCKED_MEMBER, details);
			}			
		}

		/* 
		 * 소셜id를 받아와도, 기존에 멤버로 되어있지 않으면 지금 단계에서는 member 추가 X
		 * 대신 socialId 와 loginType을 세션에 저장하여 register에서 닉네임을 설정할 때
		 * member에 추가함
		 */
				
		
		// 로그인시 알림생성
		try {
			int participantId = member.getId();
			List<Participation> unevaluatedList = participationDao.getUnevaluatedListByParticipantId(participantId);
			
			// 용도가 평가하지않은 모임 찾기
			if(unevaluatedList!=null && !unevaluatedList.isEmpty()){
				createNotification(participantId,"/my-meeting",1);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
		    System.err.println("Error: the array is empty!");
		}

		/*
		 * 회원가입이 완료되어있는 멤버는
		 * SecurityContextHolder에 인증정보 등록 후 로그인 완료
		 */
		UserDetails user = userSecurityService.loadUserByUsername(member.getNickname());

		Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
		result.put("code", "success");
		result.put("id", member.getId());
		result.put("nickname", member.getNickname());
		result.put("profileImagePath", member.getProfileImagePath());
		result.put("fame", member.getFame());

		return ResponseEntity.ok(result);
	}


	@GetMapping("me")
	@ResponseBody
	public ResponseEntity<?> getLoginInfo(@AuthenticationPrincipal ZpopUserDetails userDetails){
		Map<String, Object> result = new HashMap<>();

		if(userDetails == null){
			result.put("code", "NOT_AUTHENTICATED");
			return ResponseEntity.ok(result);
		}

		result.put("code", "AUTHENTICATED");
		result.put("id", userDetails.getId());
		result.put("nickname", userDetails.getUsername());
		result.put("profileImagePath", userDetails.getProfileImagePath());
		result.put("fame", userDetails.getFame());

		return ResponseEntity.ok(result);

	}
	
	private void createNotification(int memberId, String url, int type) {
		notificationDao.insertNotification(memberId,url,type);
	}
}
