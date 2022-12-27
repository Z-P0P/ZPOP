package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.AdminReportedMemberDto;
import com.zpop.web.entity.Notification;

@Mapper
public interface ReportedMemberDao {

	List<AdminReportedMemberDto> getAdminViewList(int size, int offset, Object object, Object object2);

	int count(String keyword, String option);
	
}