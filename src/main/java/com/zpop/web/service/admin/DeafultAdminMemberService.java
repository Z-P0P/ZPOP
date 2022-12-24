package com.zpop.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dto.admin.AdminMemberDto;
import com.zpop.web.entity.Member;
import com.zpop.web.service.MemberService;

@Service
public class DeafultAdminMemberService implements AdminMemberService{

	private final MemberDao memberDao;
	
	@Autowired
	public DeafultAdminMemberService(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
	
	@Override
	public List<Member> getList(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<Member> list = memberDao.getList(size, offset, null, null);
		return list;
	}

	@Override
	public int count(String keyword, String option) {
		int count = memberDao.countBySearch(keyword, option);
		return count;
	}
	
}
