package com.zpop.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.Notification;

@Mapper
public interface NotificationDao {

	// 알림 리스트 불러오기
	List<Notification> getListByMemberId(int id);

	// 알림을 읽었을 때, readAt을 읽은 시각으로 설정
	void update(int id, Date readAt);
	void updateByType(int memberId, Date readAt, int type);

	// 댓글 알림 생성 
	void insertCommentNotification(int memberId, String url, int type);
	
}