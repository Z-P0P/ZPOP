package com.zpop.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.service.admin.AdminMeetingService;

@Controller("adminMeetingController")
@RequestMapping("/admin/meeting")
public class MeetingController {

	private final AdminMeetingService adminMeetingService;
	
	@Autowired
	public MeetingController (AdminMeetingService adminMeetingService) {
		this.adminMeetingService = adminMeetingService;
	}
	
	@GetMapping()
	public String meeting() {
		return "redirect:meeting/list?p=1";
	}
	
	@GetMapping("list")
	public String getList(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
		
		List<AdminMeetingDto> meetings = adminMeetingService.getList(page,keyword,option);
		int count = adminMeetingService.count(keyword, option);
		model.addAttribute("meetings", meetings);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/meeting/list";
	}
	

}
