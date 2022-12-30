package com.zpop.web.service;

import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.dto.RegisterMeetingRequest;
import com.zpop.web.dto.RegisterMeetingResponse;
import com.zpop.web.entity.Member;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.entity.Participation;

import com.zpop.web.entity.meeting.Meeting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface MeetingService {

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

			RegisterMeetingResponse getActiveOptions();

			int register(RegisterMeetingRequest dto, List<MultipartFile> images, String realPath) throws FileNotFoundException, IOException;
}
