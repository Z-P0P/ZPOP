package com.zpop.web.service;

import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Member;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.entity.Participation;

import com.zpop.web.entity.meeting.Meeting;
import java.util.List;


public interface MeetingService {

	List<MeetingThumbnailResponse> getList();

	List<MeetingThumbnailResponse> getList(int startId, String keyword, Integer categoryId, String regionIds, Boolean isClosed);
	
	int register(Meeting meeting);

	int participate(Participation participation);

	MeetingDetailDto getById(int id);

	List<MeetingParticipantsDto> getParticipants(int meetingId);

    boolean delete(int id, Member member);

	void updateViewCount(int id);

}
