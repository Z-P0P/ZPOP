package com.zpop.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.NotificationDao;
import com.zpop.web.entity.Notification;
import com.zpop.web.utils.ElapsedTimeCalculator;

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
			n.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(n.getCreatedAt()));
		}
		
		for(Notification n : list) {
			if(n.getMemberId()==testMemberId) {
				unreadList.add(n);
			}
		}
		
		return unreadList;
	}

	@Override
	public void updateReadAt(int id, Date readAt) {
		
		dao.update(id,readAt);
		
	}

	@Override
	public void updateByType(Date readAt, int type) {
		dao.updateByType(readAt, type);
		
	}
	

}