package com.zpop.web.dao;

import com.zpop.web.dto.MeetingThumbPageable;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingDao {
    int insert(Meeting meeting);
    Meeting get(int id);
    List<MeetingThumbView> getThumbList(MeetingThumbPageable pageable);


    //내가 참여한 모임을 받아올 함수
    List<MeetingThumbView> getMeetingList(int memberId);
    
//    int update(Meeting meeting);
}


