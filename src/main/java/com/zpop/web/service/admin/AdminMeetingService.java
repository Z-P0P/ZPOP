package com.zpop.web.service.admin;

import java.util.List;

import com.zpop.web.dto.admin.AdminCategoryDto;
<<<<<<< HEAD
import com.zpop.web.dto.admin.AdminMeetingDetailsResponse;
=======
>>>>>>> dev
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.dto.admin.AdminRegionDto;

public interface AdminMeetingService {
	
	int count(String keyword, String option);

	List<AdminMeetingDto> getList(int page, String keyword, String option);

	List<AdminCategoryDto> getCategory(int page, String keyword, String option);
	
	int countCategory(String keyword, String option);

	int countRegion(String keyword, String option);

	List<AdminRegionDto> getRegion(int page, String keyword, String option);
<<<<<<< HEAD

	AdminMeetingDetailsResponse getDetailsResponse(int id);
=======
>>>>>>> dev
}
