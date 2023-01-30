package com.zpop.web.controller.admin;

import java.util.Date;
import java.text.DateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.dto.admin.AdminCategoryResponse;
import com.zpop.web.dto.admin.AdminRegionDto;
import com.zpop.web.dto.admin.AdminRegionResponse;
import com.zpop.web.dto.admin.meeting.AdminMeetingDetailsResponse;
import com.zpop.web.dto.admin.meeting.AdminMeetingDto;
import com.zpop.web.dto.admin.meeting.AdminMeetingListResponse;
import com.zpop.web.dto.admin.meeting.CountPerDateDto;
import com.zpop.web.service.admin.AdminMeetingService;

@Controller("adminMeetingController")
@RequestMapping("api/admin/meeting")
@RestController
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
	public AdminMeetingListResponse getList(
			@RequestParam(name="page", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable Integer period
			,@RequestParam @Nullable @DateTimeFormat(iso=ISO.DATE) Date minDate
			,@RequestParam(name="option", defaultValue = "title") String option
			,@RequestParam(name="num", defaultValue="10") @Nullable Integer num
			,@RequestParam(name="order", defaultValue = "desc") String order) {
		
		List<AdminMeetingDto> meetings = adminMeetingService.getList(page,keyword,option,period,minDate,num,order);
		int count = adminMeetingService.count(keyword,option,period,minDate);

		return AdminMeetingListResponse
									.builder()  
									.meetings(meetings)
									.count(count)
									.build();

	}
	
	@GetMapping("category")
	public AdminCategoryResponse getCategory(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
	
		List<AdminCategoryDto> categories = adminMeetingService.getCategory(page,keyword,option);
		int count = adminMeetingService.countCategory(keyword, option);
		return AdminCategoryResponse.builder()
									.count(count)
									.categories(categories)
									.build();
	}
	
	@GetMapping("region")
	public AdminRegionResponse getRegion(Model model
			,@RequestParam(name="p", defaultValue="1") int page
			,@RequestParam @Nullable String keyword
			,@RequestParam @Nullable String option) {
	
		List<AdminRegionDto> regions = adminMeetingService.getRegion(page,keyword,option);
		int count = adminMeetingService.countRegion(keyword, option);
		return AdminRegionResponse.builder()
										.count(count)
										.regions(regions)
										.build();
		}
	
	@GetMapping("{id}")
	public AdminMeetingDetailsResponse getDetailInfo(@PathVariable("id") int id) {
		
		AdminMeetingDetailsResponse result = adminMeetingService.getDetailsResponse(id);
		return result;
	}

	@PutMapping("region")
	public ResponseEntity<Object> changeRegionStatus(@RequestParam("ids") List<Integer> ids, Boolean activate){

		int result = adminMeetingService.changeRegionStatus(ids, activate);

		return ResponseEntity.ok().build();
	}

	@PutMapping("category")
	public ResponseEntity<Object> changeCategoryState(@RequestParam("ids") List<Integer> ids, Boolean activate){

		int result = adminMeetingService.changeCategoryStatus(ids, activate);

		return ResponseEntity.ok().build();
	}
	

	@DeleteMapping("{id}")
	public Date delete(@PathVariable("id") int id,
					@RequestParam boolean getDeleted) {
		
		Date result = adminMeetingService.deleteMeeting(id, getDeleted);
		return result;
	}

	@DeleteMapping()
	public int deleteAll(@RequestParam("ids") List<Integer> ids,
					@RequestParam boolean getDeleted) {
		int result = adminMeetingService.deleteAllMeeting(ids, getDeleted);
		return result;
	}

	@GetMapping("/count/latest")
	public List<CountPerDateDto> getLatestMeetingCount(){
		return adminMeetingService.getLatestMeetingCount();
	}
}
