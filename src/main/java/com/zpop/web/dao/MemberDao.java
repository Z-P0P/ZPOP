package com.zpop.web.dao;

import java.util.List;

import com.zpop.web.entity.Member;

public interface MemberDao {
	List<Member> getList(int size, int offset, String keyword, String option);
	Member get(int id);
	int insert(Member member);
	int update(Member member);
	int count(int socialTypeId);
	
}
