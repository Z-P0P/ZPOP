package com.zpop.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zpop.web.entity.Notification;
import com.zpop.web.service.NotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationController {
	
	@Autowired NotificationService service;
	

	@GetMapping("/test")
	public String notificationTest() {
		
		return"notification/notification-modal";
	}
	
	@GetMapping
	@ResponseBody
	public  List<Notification> getMemberId() {
		
		List<Notification> list = service.getNotification();		
		
		return list;
	}
	
}
