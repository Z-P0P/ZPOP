package com.zpop.web.service.admin;

import java.util.List;

import com.zpop.web.entity.Member;

public interface AdminMemberService {

	List<Member> getList(int page, String keyword, String option);

	int count(String keyword, String option);

}
