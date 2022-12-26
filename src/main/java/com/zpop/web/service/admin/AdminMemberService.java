package com.zpop.web.service.admin;

import java.util.List;

import com.zpop.web.dto.admin.AdminMemberEvalDto;
import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.entity.Member;

public interface AdminMemberService {

	List<Member> getList(int page, String keyword, String option);
	int countMember(String keyword, String option);
	
	List<AdminMemberEvalDto> getEvalList(int page, String keyword, String option);
	int countEval(String keyword, String option);
	int countParticipation(String keyword, String option);
	List<AdminParticipationDto> getParticipationList(int page, String keyword, String option);
}
