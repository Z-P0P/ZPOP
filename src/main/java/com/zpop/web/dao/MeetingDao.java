package com.zpop.web.dao;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.ParticipantDto;
import com.zpop.web.dto.UpdateMeetingViewDto;
import com.zpop.web.dto.admin.AdminMeetingDetailsDto;
import com.zpop.web.dto.admin.AdminMeetingDto;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeetingDao {
    int insert(Meeting meeting);

    MeetingDetailDto getDetailView(int id); 

    Meeting get(int id);

    List<MeetingThumbnailView> getMeetingList(int id);

    List<MeetingThumbnailView> getThumbnailViewList(MeetingThumbnailPagination pagination);
    
    int updateDeletedAt(Meeting meeting);

	AdminMeetingDetailsDto getAdminDetailView(int meetingId);

	List<ParticipantDto> getParticipants(int id);

    void updateViewCount(int id);

	List<AdminMeetingDto> getAdminViewList(int size, int offset, String keyword, String option);

	int count(String keyword, String option);

	int updateClosedAt(Meeting foundMeeting);

	int getMaxMember(int meetingId);

	int updateContent(Meeting meeting);

	int update(Meeting meeting);

	UpdateMeetingViewDto getUpdateView(int id);
}

