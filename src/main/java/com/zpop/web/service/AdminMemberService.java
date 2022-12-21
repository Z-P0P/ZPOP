package com.zpop.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.entity.Member;

@Service
public class AdminMemberService implements MemberService{

	private final MemberDao memberDao;
	
	@Autowired
	public AdminMemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public List<Member> getList(int page) {
		int size = 10;
		int offset=(page-1)*size;
		List<Member> list = memberDao.getList(size, offset, null, null);
		return list;
	}

	@Override
	public int getSearchCount(String keyword, String option) {
		int count = memberDao.countBySearch(keyword, option);
		return count;
	}

	
}
