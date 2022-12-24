package com.zpop.web.dao;

import com.zpop.web.entity.MemberEval;

public interface MemberEvalDao {

	//member평가

	int insert (MemberEval memberEval);
	int insertAll(MemberEval[] memberEvals);
	
//	int delete(MemberEval memberEval); -> 악성회원인 경우에 평가한 기록을 삭제할 수 있도록
}
