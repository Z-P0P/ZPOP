package com.zpop.web.controller.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.dto.admin.meeting.CountPerDateDto;
import com.zpop.web.dto.admin.member.AdminMemberEvalDto;
import com.zpop.web.entity.Member;
import com.zpop.web.service.admin.AdminMemberService;

@RestController("adminMemberController")
@RequestMapping("/api/admin/member")
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
	public Map<String, Object>  getList(
		@RequestParam(name="page", defaultValue="1") int page
		,@RequestParam @Nullable String keyword
		,@RequestParam @Nullable Integer period
		,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
		,@RequestParam (name="option", defaultValue = "title") @Nullable String option
		,@RequestParam(name="num", defaultValue="10") @Nullable Integer num
		,@RequestParam(name="order", defaultValue = "desc") @Nullable String order) {
		
				Map<String, Object> result = new HashMap<>();

		List<Member> members = adminMemberService.getList(page,keyword,option,period, minDate,num, order);
		int count = adminMemberService.countMember(keyword, option,period,minDate);
		result.put("members", members);
		result.put("count",count);

		return result;
	}
	
	@GetMapping("eval")
	public Map<String, Object>  getMemberEvalList(
	@RequestParam(name="page", defaultValue="1") int page
	,@RequestParam @Nullable String keyword
	,@RequestParam @Nullable Integer period
	,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
	,@RequestParam (name="option", defaultValue = "title") @Nullable String option
	,@RequestParam(name="num", defaultValue="10") @Nullable Integer num
	,@RequestParam(name="order", defaultValue = "desc") @Nullable String order) {
		
		Map<String, Object> result = new HashMap<>();
		List<AdminMemberEvalDto> evaluations = adminMemberService.getEvalList(page,keyword,option,
																				period, minDate,num, order);
		int count = adminMemberService.countEval(keyword, option,period,minDate,order);
		result.put("evaluations", evaluations);
		result.put("count", count);
		
		return result;
	}
	

	@GetMapping("participation")
	public Map<String, Object>  getParticipation(Model model
			,@RequestParam(name="page", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		
		Map<String, Object> result = new HashMap<>();

		List<AdminParticipationDto> participations = adminMemberService.getParticipationList(page,keyword,option);
		int count = adminMemberService.countParticipation(keyword, option);
		result.put("participations", participations);
		result.put("count",count);
		
		return result;
	}

	@GetMapping("count/latest")
	public List<CountPerDateDto> getLatestCountPerDay() {
		
		return adminMemberService.getLatestCountPerDay();
		
	}
}
