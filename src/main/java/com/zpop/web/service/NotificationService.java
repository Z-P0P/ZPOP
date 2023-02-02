package com.zpop.web.service;

import java.util.Date;
import java.util.List;

import com.zpop.web.entity.Notification;

public interface NotificationService {
	

	/**
	 * 멤버의 읽지 않은 알림을 모두 조회한다.
	 *
	 * 회원아이디로 읽지 않은 알림을 모두 가져온다. {@link Integer 회원아이디}
	 * 
	 * @param memberId 멤버아이디
	 * @return 알림 리스트
	 */
	List<Notification> getNotificationByMemberId(int memberId);
	
	void updateReadAt(int id, Date readAt);
	
	void updateByType(int memberId, Date readAt, int type);

	void updateAllById(int memberId, Date readAt);
	
}
