package com.zpop.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.report.AdminReportedMeetingDto;
import com.zpop.web.entity.ReportedMeeting;

@Mapper
public interface ReportedMeetingDao {

	List<AdminReportedMeetingDto> getAdminViewList(int size, int offset, String keyword, String option, Date minDate, Integer period, String order);

	int count(String keyword, String option, Date minDate, Integer period);
	
	int insert(ReportedMeeting reportedMeeting);
	
	int[] getReportedMeetingId(int meetingId, int reporterId);

	int updateAll(List<Integer> ids, Date releasedAt);

	List<ReportedMeeting> getByIds(List<Integer> ids);
	
}