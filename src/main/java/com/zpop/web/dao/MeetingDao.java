package com.zpop.web.dao;

import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingDao {
    int insert(Meeting meeting);

    Meeting get(int id);

    List<MeetingThumbnailView> getThumbnailViewList(MeetingThumbnailPagination pagination);

    int updateDeletedAt(Meeting meeting);

    List<MeetingThumbnailView> getMeetingList(int id);

	List<AdminMeetingDto> getAdminViewList(int size, int offset, String keyword, String option);
	int count(String keyword, String option);
	
}
