package com.zpop.web.service;


import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.MeetingRegisterDto;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Category;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.Region;
import com.zpop.web.entity.meeting.Meeting;
import java.util.List;
import java.util.Map;

public interface MeetingService {
    List<MeetingThumbnailResponse> getList(int startId, String keyword, Boolean isClosed);
	int register(Meeting meeting);
	int participate(Participation participation);
	MeetingDetailDto getById(int id);
	List<MeetingParticipantsDto> getParticipants(int meetingId);
}
