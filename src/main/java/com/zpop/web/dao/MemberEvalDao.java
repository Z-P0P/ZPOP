package com.zpop.web.dao;

import java.util.List;

import com.zpop.web.entity.MemberEval;

public interface MemberEvalDao {
    
	List<MemberEval> getList(int offset, int size, String option, String keyword);
    int insert(MemberEval eval);
    int insertAll(MemberEval[] evals);
}
