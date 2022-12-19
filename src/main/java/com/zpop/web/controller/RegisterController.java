package com.zpop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/register")
public class RegisterController {
	private final int MAX_NICKNAME_LENGTH = 10;

	@Autowired
	private MemberDao memberDao;

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

		System.out.println(nickname);
		if (nickname.isBlank() || nickname.isEmpty() || nickname.length() > MAX_NICKNAME_LENGTH) {
			return "nickname_not_allowed";
		}

		String socialId = (String) session.getAttribute("socialId");
		if (socialId == null) {
			return "not_login_user";
		}
		System.out.println("유효성 요청 옴");
		Member registeredMemberWithNickname = memberDao.getByNickname(nickname);
		if (registeredMemberWithNickname != null) {
			return "nickname_already_used";
		}

		return "nickname_allowed";
	}

	@PostMapping("/nickname/set")
	@ResponseBody
	public String setNickname(@RequestParam String nickname, HttpSession session) {

		if (nickname.isBlank() || nickname.isEmpty() || nickname.length() > MAX_NICKNAME_LENGTH) {
			return "nickname_not_allowed";
		}

		String socialId = (String) session.getAttribute("socialId");
		int socialIdType = (Integer) session.getAttribute("socialIdType");
		if (socialId == null) {
			return "not_login_user";
		}

		Member registeredMemberWithNickname = memberDao.getByNickname(nickname);
		if (registeredMemberWithNickname != null) {
			return "nickname_already_used";
		}

		Member member = new Member();
		member.setNickname(nickname);
		member.setSocialId(socialId);
		member.setSocialTypeId(socialIdType);
		int result = memberDao.insert(member);
		if (result == 0) {
			System.out.println("회원 등록에 실패하였습니다.");
		}

		int memberId = memberDao.getBySocialId(socialId).getId();
		session.setAttribute("memberId", memberId);
		session.setAttribute("nickname", nickname);

		return "nickname_created";
	}
	
}
