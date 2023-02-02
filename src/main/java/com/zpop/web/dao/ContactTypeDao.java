package com.zpop.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.ContactTypeDto;
import com.zpop.web.entity.ContactType;

@Mapper
public interface ContactTypeDao {
	
	List<ContactType> getList();
	List<ContactTypeDto> getActiveList();
}
