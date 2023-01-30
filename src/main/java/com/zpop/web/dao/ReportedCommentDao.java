package com.zpop.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.ReportedComment;
import com.zpop.web.dto.admin.report.AdminReportedCommentDto;

@Mapper
public interface ReportedCommentDao {

	List<AdminReportedCommentDto> getAdminViewList(int size, int offset, String keyword, String option, Date minDate, Integer period, String order);

	int count(String keyword, String option, Date minDate, Integer period);
	
	int insert(ReportedComment reportedComment);
	
	int[] select(int commentId, int reporterId);

    int updateAll(List<Integer> ids, Date releasedAt);
	
	List<ReportedComment> getByIds(List<Integer> ids);
}