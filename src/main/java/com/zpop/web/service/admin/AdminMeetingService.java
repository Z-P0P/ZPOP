package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.dto.admin.AdminMeetingDetailsResponse;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.dto.admin.AdminRegionDto;

public interface AdminMeetingService {
	
	int count(String keyword, String option, Integer period, Date minDate);

	List<AdminMeetingDto> getList(int page, String keyword, String option, Integer period, Date minDate, Integer num, String order);

	List<AdminCategoryDto> getCategory(int page, String keyword, String option);
	
	int countCategory(String keyword, String option);

	int countRegion(String keyword, String option);

	List<AdminRegionDto> getRegion(int page, String keyword, String option);

	AdminMeetingDetailsResponse getDetailsResponse(int id);

	int changeRegionStatus(List<Integer> ids, Boolean activate);

	int changeCategoryStatus(List<Integer> ids, Boolean activate);

    Date deleteMeeting(int id, boolean getDeleted);

    int deleteAllMeeting(List<Integer> ids, boolean getDeleted);

}
