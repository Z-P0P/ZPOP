package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.MemberEvalDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.dto.admin.meeting.CountPerDateDto;
import com.zpop.web.dto.admin.member.AdminMemberEvalDto;
import com.zpop.web.entity.Member;

@Service
public class DeafultAdminMemberService implements AdminMemberService{

	private final MemberDao memberDao;
	private final MemberEvalDao memberEvalDao;
	private final ParticipationDao participationDao;
	
	@Autowired
	public DeafultAdminMemberService(MemberDao memberDao
									,MemberEvalDao memberEvalDao
									,ParticipationDao participationDao) {
		this.memberDao = memberDao;
		this.memberEvalDao =memberEvalDao;
		this.participationDao = participationDao;
	}
	
	@Override
	public List<Member> getList(int page, String keyword, String option, Integer period, Date minDate, Integer num, String order) {
		int size = num;
		int offset=(page-1)*size;
		List<Member> list = memberDao.getListBySearch(size, offset, keyword, option,period,minDate,order);
		return list;
	}

	@Override
	public int countMember(String keyword, String option, Integer period, Date minDate) {
		int count = memberDao.countBySearch(keyword, option,period,minDate);
		return count;
	}

	@Override
	public List<AdminMemberEvalDto> getEvalList(int page, String keyword, String option, 
												Integer period, Date minDate, Integer num, String order) {
		int size = num;
		int offset=(page-1)*size;
		List<AdminMemberEvalDto> list = memberEvalDao.getAdminViewList(size, offset, keyword, option, period, minDate, order);
		return list;
	}
	
	public int countEval(String keyword, String option, Integer period, Date minDate, String order) {
		int count = memberEvalDao.countBySearch(keyword, option,period, minDate, order);
		return count;
	}

	@Override
	public int countParticipation(String keyword, String option) {
		int count = participationDao.countBySearch(keyword, option);
		return count;
	}

	@Override
	public List<AdminParticipationDto> getParticipationList(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<AdminParticipationDto> list = participationDao.getAdminViewList(size, offset, keyword, option);
		return list;
	}

	@Override
	public List<CountPerDateDto> getLatestCountPerDay() {
		
		return memberDao.countCreatedAtPerDay();

	}
}
