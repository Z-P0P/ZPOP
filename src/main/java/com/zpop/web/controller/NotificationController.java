package com.zpop.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zpop.web.dto.NotificationDto;
import com.zpop.web.entity.Notification;
import com.zpop.web.security.ZpopUserDetails;
import com.zpop.web.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {
	
	@Autowired NotificationService service;
	
	@GetMapping("/{id}")
	@ResponseBody
	public  List<Notification> getList(
			 @PathVariable("id") int id) {
		List<Notification> list = service.getNotificationByMemberId(id);
		List<Notification> notificationList = new ArrayList<>();
		
		int typeOneCounter = 0;
		for(Notification n : list){
			if(n.getType()==1){ 
				typeOneCounter++;
					if(typeOneCounter==1) notificationList.add(n);}
			else notificationList.add(n);
		}
		return notificationList; 
	}
	
	@PostMapping("/read/{id}")
	@ResponseBody
	public String readNotification(@PathVariable int id) {
		
		Date today = new Date();
		service.updateReadAt(id, today);
		return "test";
	}

	@PostMapping("/readAll/{id}")
	@ResponseBody
	public String readAllNotification(@PathVariable int id) {
		
		Date today = new Date();
		service.updateAllById(id, today);
		return "wow";
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
