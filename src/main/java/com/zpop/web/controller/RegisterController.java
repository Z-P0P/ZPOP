package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.entity.Member;
import com.zpop.web.service.RegisterService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	private RegisterService registerService;

	@GetMapping()
	public String register(HttpSession session) {
		if (session.getAttribute("socialId") == null || session.getAttribute("memberId") != null) {
			System.out.println("비정상적 접근");
			return "redirect:/login";
		}
		System.out.println("정상 접근");
		return "register";
	}

	@PostMapping("/nickname/validation")
	@ResponseBody
	public String validateNickname(@RequestParam String nickname, HttpSession session) {

		String socialId = (String) session.getAttribute("socialId");
		if (socialId == null) {
			return "not_login_user";
		}
		
		boolean isValid = registerService.checkNicknameValid(nickname);
		if (!isValid) {
			return "nickname_not_allowed";
		}

		boolean hasRegistered = registerService.checkNicknameRegisted(nickname);
		
		if (hasRegistered) {
			return "nickname_already_used";
		}

		return "nickname_allowed";
	}

	@PostMapping("/nickname/set")
	@ResponseBody
	public String setNickname(@RequestParam String nickname, HttpSession session) {

		String socialId = (String) session.getAttribute("socialId");
		if (socialId == null) {
			return "not_login_user";
		}
		
		boolean isValid = registerService.checkNicknameValid(nickname);
		if (!isValid) {
			return "nickname_not_allowed";
		}

		boolean hasNicknameRegistered = registerService.checkNicknameRegisted(nickname);
		
		if (hasNicknameRegistered) {
			return "nickname_already_used";
		}
		
		String loginType = (String) session.getAttribute("loginType");
		
		Member member = registerService.registerMember(nickname, socialId, loginType);
		
		if (member == null) {
			System.out.println("회원 등록에 실패하였습니다.");
		}

		// 회원 등록이 되었으므로 임시로 저장해둔 소셜id와 login타입은 제거하고, member객체를 추가
		session.removeAttribute("socialId");
		session.removeAttribute("loginType");
		session.setAttribute("member", member);

		return "nickname_created";
	}
}
