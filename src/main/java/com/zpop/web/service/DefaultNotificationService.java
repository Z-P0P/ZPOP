package com.zpop.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.NotificationDao;
import com.zpop.web.entity.Notification;

@Service
public class DefaultNotificationService implements NotificationService {

	@Autowired
	private NotificationDao dao;
	
	@Override
	public List<Notification> getNotification() {
		
		int testMemberId = 2;
		List<Notification> list = dao.getList();
		List<Notification> unreadList = new ArrayList();
		
		for(Notification n : list) {
			System.out.println(n);
			if(n.getMemberId()==testMemberId)
				unreadList.add(n);
		}
		return unreadList;
	}
	

}