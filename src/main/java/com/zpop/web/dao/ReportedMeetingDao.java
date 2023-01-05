package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.ReportedMeeting;
import com.zpop.web.dto.admin.AdminReportedMeetingDto;

@Mapper
public interface ReportedMeetingDao {

	List<AdminReportedMeetingDto> getAdminViewList(int size, int offset, Object object, Object object2);

	int count(String keyword, String option);
	
	int insert(ReportedMeeting reportedMeeting);
	
	int[] getReportedMeetingId(int meetingId, int reporterId);
	
}