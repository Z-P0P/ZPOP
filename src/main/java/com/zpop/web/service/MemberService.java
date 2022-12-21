package com.zpop.web.service;

import java.util.List;

import com.zpop.web.entity.Member;

public interface MemberService {

	public List<Member> getList(int page);

	public int getSearchCount(String keyword, String option);
	
}
