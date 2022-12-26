package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.AdminMemberEvalDto;
import com.zpop.web.entity.MemberEval;

@Mapper
public interface MemberEvalDao {

	List<AdminMemberEvalDto> getAdminViewList(int size, int offset, String keyword, String option);
	int insert (MemberEval memberEval);
	int insertAll(MemberEval[] memberEvals);
	int countBySearch(String keyword, String option);
	
//	int delete(MemberEval memberEval); -> 악성회원인 경우에 평가한 기록을 삭제할 수 있도록
}
