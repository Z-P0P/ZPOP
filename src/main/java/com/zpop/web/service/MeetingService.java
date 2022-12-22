package com.zpop.web.service;


import com.zpop.web.dto.MeetingRegisterDto;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;
import java.util.List;

public interface MeetingService {
    List<MeetingThumbnailResponse> getList(int startId, String keyword, Boolean isClosed);
	int register(Meeting meeting);
	int participate(Participation participation);
	
}
