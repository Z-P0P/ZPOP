package com.zpop.web.dao;

import java.util.List;
import java.util.Map;

import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.entity.Participation;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.entity.Participation;

@Mapper
public interface ParticipationDao {
	
    int insert(int meetingId, int memberId);
    Participation get(int id);
    
    List<Participation> getListByMeetingId(int meetingId);
    List<Participation> getList();
    List<MeetingParticipantsDto>getByMeetingId(int meetingId);
    List<AdminParticipationDto> getAdminViewList(int size, int offset, String keyword, String option);
	
    int countBySearch(String keyword, String option);
}
