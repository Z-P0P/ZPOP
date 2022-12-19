package com.zpop.web.dao;

import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingDao {
    int insert(Meeting meeting);
    Meeting get(int id);
    List<MeetingThumbnailView> getThumbnailViewList(MeetingThumbnailPagination pagination);
//    int update(Meeting meeting);
}
