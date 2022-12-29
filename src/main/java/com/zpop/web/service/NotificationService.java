package com.zpop.web.service;

import java.util.Date;
import java.util.List;

import com.zpop.web.entity.Notification;

public interface NotificationService {
	
	List<Notification> getNotification();
	
	void updateReadAt(int id, Date readAt);
	
	void updateByType(Date readAt, int type);
	
}
