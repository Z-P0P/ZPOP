package com.zpop.web.service;

import java.util.Date;
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
	public List<Notification> getNotificationByMemberId(int memberId) {
		
		List<Notification> notificationList = dao.getListByMemberId(memberId);
		
		for(Notification n : notificationList) {
			n.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(n.getCreatedAt()));
		}
		return notificationList;
	}

	@Override
	public void updateReadAt(int id, Date readAt) {
		
		dao.update(id,readAt);
		
	}

	@Override
	public void updateByType(int memberId, Date readAt, int type) {
		dao.updateByType(memberId, readAt, type);
		
	}

	@Override
	public void updateAllById(int memberId, Date readAt) {
		dao.updateAllById(memberId, readAt);
	}

}