package com.zpop.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zpop.web.entity.Member;
import com.zpop.web.service.MemberService;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController {

	private final MemberService memberService;
	
	@Autowired
	public MemberController (MemberService memberService) {
		this.memberService = memberService;
	}
	
	@GetMapping()
	public String member(Model model
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<Member> members = memberService.getList(1);
		int count = memberService.getSearchCount(keyword, option);
		model.addAttribute("members", members);
		model.addAttribute("count",count);
		
		return "admin/member/list";
	}
}
