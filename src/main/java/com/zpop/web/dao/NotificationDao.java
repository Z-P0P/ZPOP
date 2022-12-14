package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.Notification;

@Mapper
public interface NotificationDao {

	// 알림 리스트 불러오기
	List<Notification> getList(int id);

	Notification get(int id);

	// 알림을 읽었을 때, readAt을 읽은 시각으로 설정
	boolean updateReadDate(int id);

}