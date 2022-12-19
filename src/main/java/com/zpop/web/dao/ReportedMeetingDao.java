package com.zpop.web.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.ReportedMeeting;

@Mapper
public interface ReportedMeetingDao {
	
	int insert(ReportedMeeting reportedMeeting);
	
}