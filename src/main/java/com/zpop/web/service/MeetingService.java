package com.zpop.web.service;

import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Member;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.entity.Participation;

import com.zpop.web.entity.meeting.Meeting;
import java.util.List;


public interface MeetingService {
			int register(Meeting meeting);

			List<MeetingThumbnailResponse> getList();
			List<MeetingThumbnailResponse> getList(String keyword);
			List<MeetingThumbnailResponse> getList(int startId, String keyword, Integer categoryId, String regionIds, Boolean isClosed);

			MeetingDetailDto getById(int id);

			List<MeetingParticipantsDto> getParticipants(int id);

			int participate(int meetingId, int memberId);

			boolean cancelParticipate(int id, int memberId);

			void updateViewCount(int id);

			boolean kick(int id, int participantId, Member member);

			boolean close(int id, Member member);

			boolean delete(int id, Member member);
}
