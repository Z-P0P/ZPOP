package com.zpop.web.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.SocialTypeDao;
import com.zpop.web.entity.Member;
import com.zpop.web.service.LoginService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

	private LoginService loginService;
	private Map<String, LoginService> loginServiceMap;

	@Autowired
	public LoginController(MemberDao memberDao, 
			Map<String, LoginService> loginServiceMap
			, SocialTypeDao socialTypeDao) {
		this.loginServiceMap = loginServiceMap;
	}
	
	
	@GetMapping("")
	public String loginPage(Model model, HttpSession session) {
		Member member = (Member)session.getAttribute("member");
		if (member != null) {
			model.addAttribute(member);			
		}
		return "login";
	}

	// 소셜 로그인 시, kakao, naver를 공통적으로 처리함
	@GetMapping("oauth/{loginType}")
	public String OAuthlogin(@PathVariable("loginType") String loginType
			,@RequestParam @Nullable String code 
			, @RequestParam @Nullable String state
			, HttpSession session)
			throws IOException, InterruptedException {
		
		
		if (session.getAttribute("memberId") != null) {
			System.out.println("이미 로그인한 사용자임");
			return "redirect:/login";
		}

		if (code == null) {
			System.out.println("로그인에 실패하였음");
			return "redirect:/login";
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
		if (member == null) {
			session.setAttribute("socialId", socialId);
			session.setAttribute("loginType", loginType);
			return "redirect:/register";
		}
		
		// 만약 member가 조회되면 세션에 정보를 저장하여 member정보를 view에 활용함
		session.setAttribute("member", member);
		return "redirect:/login";
		
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
}
