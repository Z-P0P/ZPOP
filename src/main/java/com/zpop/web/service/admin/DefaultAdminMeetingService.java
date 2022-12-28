package com.zpop.web.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.AgeRangeDao;
import com.zpop.web.dao.CategoryDao;
import com.zpop.web.dao.ContactTypeDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dao.RegionDao;
import com.zpop.web.dto.AgeRangeDto;
import com.zpop.web.dto.CategoryDto;
import com.zpop.web.dto.ContactTypeDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.ParticipantDto;
import com.zpop.web.dto.RegionDto;
import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.dto.admin.AdminMeetingDetailsDto;
import com.zpop.web.dto.admin.AdminMeetingDetailsResponse;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.dto.admin.AdminRegionDto;


@Service
public class DefaultAdminMeetingService implements AdminMeetingService {
	
	private final MeetingDao meetingDao;
	private final CategoryDao categoryDao;
	private final RegionDao regionDao;
	private final AgeRangeDao ageRangeDao;
	private final ContactTypeDao contactTypeDao;
	private final ParticipationDao participationDao;
	
	
	@Autowired
	public DefaultAdminMeetingService(MeetingDao meetingDao,
									CategoryDao categoryDao,
									RegionDao regionDao,
									AgeRangeDao ageRangeDao,
									ContactTypeDao contactTypeDao,
									ParticipationDao participationDao) {
		this.meetingDao = meetingDao;
		this.categoryDao = categoryDao;
		this.regionDao = regionDao;
		this.contactTypeDao = contactTypeDao;
		this.ageRangeDao = ageRangeDao;
		this.participationDao = participationDao;
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



}


	
	@Override
	public AdminMeetingDetailsResponse getDetailsResponse(int id) {
		
		AdminMeetingDetailsDto dto = meetingDao.getAdminDetailView(id);
		List<CategoryDto> categories = categoryDao.getActiveList();
		List<RegionDto> regions = regionDao.getActiveList();
		List<ContactTypeDto> contactTypes = contactTypeDao.getActiveList();
		List<AgeRangeDto> ageRanges = ageRangeDao.getActiveList();
		List<MeetingParticipantsDto> participants = participationDao.getByMeetingId(id);
		
		AdminMeetingDetailsResponse response = new AdminMeetingDetailsResponse(
									dto,
									categories,
									regions,
									contactTypes,
									ageRanges,
									participants
									);
		return response;
	}
}


	
	@Override
	public AdminMeetingDetailsResponse getDetailsResponse(int id) {
		
		AdminMeetingDetailsDto dto = meetingDao.getAdminDetailView(id);
		List<CategoryDto> categories = categoryDao.getActiveList();
		List<RegionDto> regions = regionDao.getActiveList();
		List<ContactTypeDto> contactTypes = contactTypeDao.getActiveList();
		List<AgeRangeDto> ageRanges = ageRangeDao.getActiveList();
		List<MeetingParticipantsDto> participants = participationDao.getByMeetingId(id);
		
		AdminMeetingDetailsResponse response = new AdminMeetingDetailsResponse(
									dto,
									categories,
									regions,
									contactTypes,
									ageRanges,
									participants
									);
		return response;
	}
}
