package com.zpop.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zpop.web.dto.admin.AdminMemberEvalDto;
import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.entity.Member;
import com.zpop.web.service.admin.AdminMemberService;

@Controller("adminMemberController")
@RequestMapping("/admin/member")
public class MemberController {

	private final AdminMemberService adminMemberService;
	
	@Autowired
	public MemberController (AdminMemberService adminMemberService) {
		this.adminMemberService = adminMemberService;
	}
	
	@GetMapping()
	public String member() {
		return "redirect:member/list?p=1";
	}
	
	
	@GetMapping("list")
	public String getList(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<Member> members = adminMemberService.getList(page,keyword,option);
		int count = adminMemberService.countMember(keyword, option);
		model.addAttribute("members", members);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/member/list";
	}
	
	@GetMapping("eval")
	public String getMemberEvalList(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<AdminMemberEvalDto> evaluations = adminMemberService.getEvalList(page,keyword,option);
		int count = adminMemberService.countEval(keyword, option);
		model.addAttribute("evaluations", evaluations);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/member/evaluation";
	}
	

	@GetMapping("participation")
	public String getParticipation(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		
		List<AdminParticipationDto> participations = adminMemberService.getParticipationList(page,keyword,option);
		int count = adminMemberService.countParticipation(keyword, option);
		model.addAttribute("participations", participations);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/member/participation";
	}
}
