package com.zpop.web.dao;

import java.util.List;

import com.zpop.web.entity.Member;

public interface MemberDao {
    List<Member> getList(int offset, int size, String option, String keyword);
    Member get(int id);
    
    int insert(Member member);
    int update(Member member);
    int count(int socialTypeid);
    
}
