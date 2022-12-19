package com.zpop.web.dao;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.ReportedMember;

@Mapper
public interface ReportedMemberDao {
	
	int insert(ReportedMember reportedMember);
	
}