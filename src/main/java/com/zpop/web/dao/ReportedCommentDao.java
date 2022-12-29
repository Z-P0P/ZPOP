package com.zpop.web.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.ReportedComment;
import com.zpop.web.dto.admin.AdminReportedCommentDto;
import com.zpop.web.entity.Notification;

@Mapper
public interface ReportedCommentDao {

	List<AdminReportedCommentDto> getAdminViewList(int size, int offset, Object object, Object object2);

	int count(String keyword, String option);
	
	int insert(ReportedComment reportedComment);
	
}