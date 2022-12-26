package com.zpop.web.service.admin;

import java.util.List;

import com.zpop.web.dto.admin.AdminMeetingDto;

public interface AdminMeetingService {
	
	int count(String keyword, String option);

	List<AdminMeetingDto> getList(int page, String keyword, String option);
}
