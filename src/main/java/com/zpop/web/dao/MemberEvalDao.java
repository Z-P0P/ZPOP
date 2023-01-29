package com.zpop.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.member.AdminMemberEvalDto;
import com.zpop.web.entity.MemberEval;

@Mapper
public interface MemberEvalDao {

	List<AdminMemberEvalDto> getAdminViewList(int size, int offset, String keyword, String option, Integer period, Date minDate, String order);
	int insert (MemberEval memberEval);
	int insertAll(MemberEval[] memberEvals);
	int insertAll(List<MemberEval> memberEvals);
	int countBySearch(String keyword, String option, Integer period, Date minDate, String order);
	
//	int delete(MemberEval memberEval); -> 악성회원인 경우에 평가한 기록을 삭제할 수 있도록
}