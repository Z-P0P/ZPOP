package com.zpop.web.dao;

import java.util.List;

import com.zpop.web.entity.NicknameLog;

public interface NicknameLogDao {

	List<NicknameLog> getList(int offset, int size, int memberId);
	int insert(NicknameLog log);
		
}
