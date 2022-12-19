package com.zpop.web.service;


import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Meeting;
import java.util.List;

public interface MeetingService {
    List<MeetingThumbnailResponse> getList(int startId, String keyword, Boolean isClosed);
	int register(Meeting meeting);
	boolean participate(int meetingId, int testMemberId);

}
