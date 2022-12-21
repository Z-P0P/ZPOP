package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.Member;

@Mapper
public interface MemberDao {

	List<Member> getList(int size, int offset, String keyword, String option);
	Member getById(int id);
	Member getBySocialId(String socialId);
	Member getByNickname(String nickname);
	int insert(Member member);
	int update(Member member);
	int countBySearch(String keyword, String option);
	int count(int socialTypeId);
	
}
