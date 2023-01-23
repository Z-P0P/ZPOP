package com.zpop.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.AdminReportedMemberDto;
import com.zpop.web.entity.ReportedMember;

@Mapper
public interface ReportedMemberDao {

	List<AdminReportedMemberDto> getAdminViewList(int size, int offset, String keyword, String option, Date minDate, Integer period, String order);

	int count(String keyword, String option, Date minDate, Integer period);
	
	int insert(ReportedMember reportedMember);
	
	int updateAll(List<Integer> ids, Date releasedAt);

    List<ReportedMember> getByIds(List<Integer> ids);
}