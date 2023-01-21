package com.zpop.web.dao;

import com.zpop.web.entity.NicknameLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NicknameLogDao {

    // page
    List<NicknameLog> getList(int offset, int size, int memberId);
    int insert(NicknameLog nicknameLog);
    NicknameLog getLatestByMemberId(int memberId);
}
