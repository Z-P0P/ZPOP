package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.dto.admin.meeting.CountPerDateDto;
import com.zpop.web.dto.admin.member.AdminMemberEvalDto;
import com.zpop.web.entity.Member;

public interface AdminMemberService {

	List<Member> getList(int page, String keyword, String option, Integer period, Date minDate, Integer num, String order);
	int countMember(String keyword, String option, Integer period, Date minDate);
	
	List<AdminMemberEvalDto> getEvalList(int page, String keyword, String option, Integer period, Date minDate, Integer num, String order);
	int countEval(String keyword, String option, Integer period, Date minDate, String order);
	int countParticipation(String keyword, String option);
	List<AdminParticipationDto> getParticipationList(int page, String keyword, String option);
    List<CountPerDateDto> getLatestCountPerDay();
}
