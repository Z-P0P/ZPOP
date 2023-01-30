package com.zpop.web.service.admin;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zpop.web.dao.AgeRangeDao;
import com.zpop.web.dao.CategoryDao;
import com.zpop.web.dao.ContactTypeDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dao.RegionDao;
import com.zpop.web.dto.AgeRangeDto;
import com.zpop.web.dto.CategoryDto;
import com.zpop.web.dto.ContactTypeDto;
import com.zpop.web.dto.RegionDto;
import com.zpop.web.dto.RegisterMeetingViewResponse;
import com.zpop.web.dto.admin.AdminCategoryDto;
import com.zpop.web.dto.admin.AdminRegionDto;
import com.zpop.web.dto.admin.meeting.AdminMeetingDetailsDto;
import com.zpop.web.dto.admin.meeting.AdminMeetingDetailsResponse;
import com.zpop.web.dto.admin.meeting.AdminMeetingDto;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.participation.ParticipationInfoView;


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
   public List<AdminMeetingDto> getList(int page, String keyword, String option, Integer period, Date minDate, Integer num, String order){
      // TODO Auto-generated method stub
      int size = num;
      int offset=(page-1)*size;
      List<AdminMeetingDto> list = meetingDao.getAdminViewList(size, offset, keyword, option, period, minDate, order);
      
      return list;
   }

   @Override
   public int count(String keyword, String option, Integer period, Date minDate) {
      
      int count = meetingDao.count(keyword, option,period, minDate);
      
      return count;
   }
   
   @Override
   public AdminMeetingDetailsResponse getDetailsResponse(int id) {
      
      AdminMeetingDetailsDto details = meetingDao.getAdminDetailView(id);

      
      List<CategoryDto> categories = categoryDao.getActiveList();
      List<RegionDto> regions = regionDao.getActiveList();
      List<ContactTypeDto> contactTypes = contactTypeDao.getActiveList();
      List<AgeRangeDto> ageRanges = ageRangeDao.getActiveList();
      List<ParticipationInfoView> participants = participationDao.getParticipantInfoByMeetingId(id);
      
      RegisterMeetingViewResponse options = RegisterMeetingViewResponse.builder()
                                          .categories(categories)
                                          .regions(regions)
                                          .contactTypes(contactTypes)
                                          .ageRanges(ageRanges)
                                          .build();

      AdminMeetingDetailsResponse response = AdminMeetingDetailsResponse.builder()
                           .details(details)
                           .options(options)
                           .participants(participants)
                           .build();
      return response;
   }


   @Override
   public List<AdminCategoryDto> getCategory(int page, String keyword, String option) {
      int size = 10;
      int offset=(page-1)*size;
      return categoryDao.getAdminViewList(offset,size,keyword, option);
   }


   @Override
   public int countCategory(String keyword, String option) {
      return categoryDao.count(keyword, option);
   }


   @Override
   public int countRegion(String keyword, String option) {
      return regionDao.count(keyword, option);
   }


   @Override
   public List<AdminRegionDto> getRegion(int page, String keyword, String option) {
      int size = 10;
      int offset=(page-1)*size;
      return regionDao.getAdminViewList(offset,size, keyword, option);
   }


   @Override
   public int changeRegionStatus(List<Integer> ids, Boolean activate) {
      if (ids.isEmpty()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "최소 1 개 이상의 id를 선택해야 합니다.");
      }
      int result = regionDao.updateDeletedAtAll(ids,activate);
      return result;
   }

   @Override
   public int changeCategoryStatus(List<Integer> ids, Boolean activate) {
      if (ids.isEmpty()){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "최소 1 개 이상의 id를 선택해야 합니다.");
      }
      int result = categoryDao.updateDeletedAtAll(ids,activate);
      return result;
   }


   @Override
   @Transactional
   public Date deleteMeeting(int id, boolean getDeleted) {
      Meeting meeting = new Meeting();
      meeting.setId(id);
      if (getDeleted){
         meetingDao.updateDeletedAt(meeting);
      }
      else{
         meetingDao.removeDeletedAt(meeting);
      }
      Date deletedAt = null;
      meeting = meetingDao.get(id);
      if (meeting != null){
         deletedAt = meeting.getDeletedAt();
      }

      return deletedAt;
   }


   @Override
   public int deleteAllMeeting(List<Integer> ids, boolean getDeleted) {
      if (getDeleted){
         meetingDao.updateAllDeletedAt(ids);
      }
      else{
         meetingDao.removeAllDeletedAt(ids);
      }
      return 0;
   }
}