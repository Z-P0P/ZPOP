package com.zpop.web.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.dto.admin.AdminMeetingDetailsResponse;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.dto.admin.AdminRegionDto;
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
	
	@GetMapping("category")
	public String getCategory(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
	
		List<AdminCategoryDto> categories = adminMeetingService.getCategory(page,keyword,option);
		int count = adminMeetingService.countCategory(keyword, option);
		model.addAttribute("categories", categories);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/meeting/category";
	}
	
	@GetMapping("region")
	public String getRegion(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
	
		List<AdminRegionDto> regions = adminMeetingService.getRegion(page,keyword,option);
		int count = adminMeetingService.countRegion(keyword, option);
		model.addAttribute("regions", regions);
		model.addAttribute("count",count);
		model.addAttribute("page",page);
		
		return "admin/meeting/region";
	}
	
	@RequestMapping("{id}")
	@ResponseBody
	public AdminMeetingDetailsResponse getDetailInfo(@PathVariable("id") int id) {
		
		AdminMeetingDetailsResponse result = adminMeetingService.getDetailsResponse(id);
		return result;
	}
	
}
