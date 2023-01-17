package com.zpop.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.dto.NotificationDto;
import com.zpop.web.dto.RequestCommentReportDto;
import com.zpop.web.entity.Notification;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.NotificationService;
import com.zpop.web.utils.ElapsedTimeCalculator;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired NotificationService service;
	
	@GetMapping
	@ResponseBody
	public  List<Notification> getList(
			 @AuthenticationPrincipal ZpopUserDetails userDetails
			) {
		int memberId = userDetails.getId();
		List<Notification> list = service.getNotificationByMemberId(memberId);
		
		return list; 
	}
	
	@PostMapping("/read")
	@ResponseBody
	public String readNotification(@RequestBody NotificationDto dto) {
		
		int id = Integer.parseInt(dto.getId());
		Date today = null;
		
		Boolean isRead = Boolean.parseBoolean(dto.getReadAt());
		if(isRead)
			today = new Date();
		
		service.updateReadAt(id, today);
		
		return "test";
	}
	
	@PostMapping("/type")
	@ResponseBody
	public String readNotificationByType(
			@RequestBody NotificationDto dto,
			@AuthenticationPrincipal ZpopUserDetails userDetails) {
		
		int memberId = userDetails.getId();
		Date today = null;
		Boolean isRead = Boolean.parseBoolean(dto.getReadAt());
		if(isRead)
			today = new Date();
		
		int type = Integer.parseInt(dto.getType());
		service.updateByType(memberId, today, type);
		
		return "test";
	}
	
}
