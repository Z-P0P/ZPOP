package com.zpop.web.dao;

import com.zpop.web.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.Member;

@Mapper
public interface MemberDao {
	List<Member> getList(int size, int offset, String keyword, String option);

	Member getById(int id);

	Member getBySocialId(String socialId);

	Member getByNickname(String nickname);

	Member get(int id);

	int insert(Member member);

	int update(Member member);

	int count(int socialTypeId);
}