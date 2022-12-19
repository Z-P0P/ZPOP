package com.zpop.web.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.ReportedComment;

@Mapper
public interface ReportedCommentDao {
	
	int insert(ReportedComment reportedComment);
	
}