package com.zpop.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;
import com.zpop.web.dto.admin.AdminReportedMemberDto;
import com.zpop.web.service.admin.AdminReportService;

@Controller("adminReportController")
@RequestMapping("/admin/report")
public class ReportController {

	private final AdminReportService adminReportService;
	
	@Autowired
	public ReportController (AdminReportService adminReportService) {
		this.adminReportService = adminReportService;
	}
	
	@GetMapping()
	public String member() {
		return "redirect:report/member?p=1";
	}
	
	
	@GetMapping("member")
	public String getReportedMemberList(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<AdminReportedMemberDto> reportedMembers = 
					adminReportService.getReportedMembers (page,keyword,option);
		int count = adminReportService.countReportedMembers(keyword, option);
		model.addAttribute("reportedMembers", reportedMembers);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/report/member";
	}
	
	@GetMapping("meeting")
	public String getReportedMeetingList(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<AdminReportedMeetingDto> reportedMeetings = 
					adminReportService.getReportedMeetings (page,keyword,option);
		int count = adminReportService.countReportedMeetings(keyword, option);
		model.addAttribute("reportedMeetings", reportedMeetings);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/report/meeting";
	}
	
	@GetMapping("comment")
	public String getReportedCommentList(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<AdminReportedCommentDto> reportedComments = 
					adminReportService.getReportedComments (page,keyword,option);
		int count = adminReportService.countReportedComments(keyword, option);
		model.addAttribute("reportedComments", reportedComments);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/report/comment";
	}
	
}
