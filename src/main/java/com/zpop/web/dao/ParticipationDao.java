package com.zpop.web.dao;

import com.zpop.web.dto.admin.AdminParticipationDto;
import com.zpop.web.entity.Participation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParticipationDao {
    int insert(Participation participation);
    Participation get(int id);
    List<Participation> getListByMeetingId(int meetingId);
    List<Participation> getList();
    //update(Participation participation);
    
    List<AdminParticipationDto> getAdminViewList(int size, int offset, String keyword, String option);
	int countBySearch(String keyword, String option);
}
