package com.zpop.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.entity.Participation;

@Mapper
public interface ParticipationDao {
    int insert(Participation participation);
    Participation get(int id);
    List<Participation> getList();
    //update(Participation participation);
    List<MeetingParticipantsDto>getByMeetingId(int meetingId);
    
}
