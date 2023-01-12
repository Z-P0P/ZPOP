package com.zpop.web.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.zpop.web.entity.Member;
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
		System.out.println("인증요청");
		if (userDetails != null) {
			System.out.println("이미 로그인한 사용자임");
			return ResponseEntity.badRequest().body("이미 로그인한 사용자입니다.");
		}

		if (code == null) {
			System.out.println("로그인에 실패하였음");
		}
		
		loginService = loginServiceMap.get(loginType+"LoginService");
		String accessToken = (loginService.getAccessToken(code,state));
		String socialId = loginService.getSocialId(accessToken);
		Member member = loginService.getMemberInfo(socialId);

		/* 
		 * 소셜id를 받아와도, 기존에 멤버로 되어있지 않으면 지금 단계에서는 member 추가 X
		 * 대신 socialId 와 loginType을 세션에 저장하여 register에서 닉네임을 설정할 때
		 * member에 추가함
		 */
		
		Map<String, Object> result = new HashMap<>();
		
		
		if (member == null) {
			session.setAttribute("socialId", socialId);
			session.setAttribute("loginType", loginType);
			result.put("code", "success");
			result.put("redirectUrl", "/register");
			return ResponseEntity.ok(result);
		}
		
		
		// 로그인시 알림생성
		try {
			int participantId = member.getId();
			int[] participantIds = participationDao.getListByParticipantId(participantId);
			
			if(participantIds!=null)
				for(int p : participantIds)
					createNotification(participantIds[0],"member/me/meeting",1);
			


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
		result.put("redirectUrl", "/");
		result.put("nickname", member.getNickname());
		result.put("profileImagePath", member.getProfileImagePath());
		return ResponseEntity.ok(result);
	}
/*
 * naverLogin과 관련된 별도의 컨트롤러
	@GetMapping("oauth/naver")
	public String naverLogin(@RequestParam @Nullable String code
			, @RequestParam @Nullable String state
			, HttpSession session) throws IOException, InterruptedException {

		LoginService loginService = loginServiceMap.get("naverLoginService");

		
		if (session.getAttribute("memberId") != null) {
			System.out.println("이미 로그인한 사용자임");
			return "redirect:/login";
		}

		if (code == null) {
			System.out.println("로그인에 실패하였음");
			return "redirect:/login";
		}

		System.out.println("code: " + code);
		System.out.println("state: " + state);
		String accessToken = "";

		accessToken = (loginService.getAccessToken(code, state));
		System.out.println(accessToken);
		String socialId = loginService.getSocialId(accessToken);
		int socialIdType = 2;
		Member member = memberDao.getBySocialId(socialId);
		if (member == null) {
			session.setAttribute("socialId", socialId);
			session.setAttribute("socialIdType", socialIdType);
			return "redirect:/register";
		}
		
		session.setAttribute("memberId", member.getId());
		session.setAttribute("socialId", member.getSocialId());
		session.setAttribute("nickname", member.getNickname());
		session.setAttribute("profileImg", 
				(member.getProfileImagePath()== null ? "user-profile.svg" : member.getProfileImagePath()));
		
		return "redirect:/login";

	}
*/
	
	private void createNotification(int memberId, String url, int type) {
		notificationDao.insertCommentNotification(memberId,url,type);
	}
}
