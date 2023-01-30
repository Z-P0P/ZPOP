package com.zpop.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.entity.Member;
import com.zpop.web.global.exception.CustomException;
import com.zpop.web.global.exception.ExceptionReason;
import com.zpop.web.security.UserSecurityService;
import com.zpop.web.service.RegisterService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@Autowired
	private UserSecurityService userSecurityService;


	@PostMapping("/nickname/validation")
	public ResponseEntity<?> validateNickname(String nickname, HttpSession session) {
		System.out.println(nickname);
		String socialId = (String) session.getAttribute("socialId");
		if (socialId == null) {
			return ResponseEntity.badRequest().build();
		}
		
		Map<String, Object> response = registerService.checkNicknameValid(nickname);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/nickname/set")
	public ResponseEntity<?> setNickname(String nickname, HttpSession session) {
		
		String socialId = (String) session.getAttribute("socialId");
		if (socialId == null) {
			throw new CustomException(ExceptionReason.SOCIAL_ID_ALREADY_REGISTERED);
		}
		
		Map<String, Object> response = registerService.checkNicknameValid(nickname);
		if (!response.get("result").equals("NICKNAME_VALID")) {
			return ResponseEntity.ok(response);
		}

		String loginType = (String) session.getAttribute("loginType");
		
		Member member = registerService.registerMember(nickname, socialId, loginType);
		response.put("result", "MEMBER_REGISTERED");
		
		// 회원 등록이 되었으므로 임시로 저장해둔 소셜id와 login타입은 제거하고,
		// SecurityContextHolder에 인증 정보를 추가
		session.removeAttribute("socialId");
		session.removeAttribute("loginType");



		UserDetails user = userSecurityService.loadUserByUsername(member.getNickname());

		Authentication auth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

		return ResponseEntity.ok(response);
	}
}
