package com.zpop.web.dao;

import com.zpop.web.entity.Meeting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingDao {
    int insert(Meeting meeting);
    Meeting get(int id);
    List<Meeting> getList();
//    int update(Meeting meeting);
}
