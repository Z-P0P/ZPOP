package com.zpop.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CategoryDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.RegionDao;
import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.dto.admin.AdminRegionDto;


@Service
public class DefaultAdminMeetingService implements AdminMeetingService {
	
	private final MeetingDao meetingDao;
	private final CategoryDao categoryDao;
	private final RegionDao regionDao;
	
	
	@Autowired
	public DefaultAdminMeetingService(MeetingDao meetingDao,
									CategoryDao categoryDao,
									RegionDao regionDao) {
		this.meetingDao = meetingDao;
		this.categoryDao = categoryDao;
		this.regionDao = regionDao;
	}


	@Override
	public List<AdminMeetingDto> getList(int page, String keyword, String option) {
		// TODO Auto-generated method stub
		int size = 10;
		int offset=(page-1)*size;
		List<AdminMeetingDto> list = meetingDao.getAdminViewList(size, offset, keyword, option);
		
		return list;
	}

	@Override
	public int count(String keyword, String option) {
		
		int count = meetingDao.count(keyword, option);
		
		return count;
	}


	@Override
	public List<AdminCategoryDto> getCategory(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<AdminCategoryDto> list = categoryDao.getAdminViewList(size, offset, keyword, option);
		
		return list;
	}


	@Override
	public int countCategory(String keyword, String option) {
		return categoryDao.count(keyword,option);
	}


	@Override
	public int countRegion(String keyword, String option) {
	
		return regionDao.count(keyword, option);

	}


	@Override
	public List<AdminRegionDto> getRegion(int page, String keyword, String option) {
		int size = 10;
		int offset=(page-1)*size;
		List<AdminRegionDto> list = regionDao.getAdminViewList(offset, size, keyword, option);
		
		return list;
	}



}
