package com.zpop.web.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.dto.RegisterMeetingRequest;
import com.zpop.web.dto.RegisterMeetingResponse;
import com.zpop.web.dto.RegisterMeetingViewResponse;
import com.zpop.web.dto.UpdateMeetingRequest;
import com.zpop.web.dto.UpdateMeetingViewDto;
import com.zpop.web.entity.MeetingFile;
import com.zpop.web.entity.Member;


public interface MeetingService {

			List<MeetingThumbnailResponse> getList();
			List<MeetingThumbnailResponse> getList(String keyword);
			List<MeetingThumbnailResponse> getList(int startId, String keyword, Integer categoryId, String regionIds, Boolean isClosed);

			MeetingDetailDto getById(int id);

			List<MeetingParticipantsDto> getParticipants(int id);

			int participate(int meetingId, int memberId);

			void updateViewCount(int id);

			boolean kick(int id, int participantId, Member member);

			boolean close(int id, Member member);

			boolean delete(int id, Member member);

			RegisterMeetingViewResponse getActiveOptions();

			RegisterMeetingResponse register(RegisterMeetingRequest dto) throws FileNotFoundException, IOException;
			
			boolean updateMeeting(UpdateMeetingRequest dto) throws IOException;
			
			UpdateMeetingViewDto getUpdateMeetingView(int id);
			
			MeetingFile uploadFile(MultipartFile file, String path) throws IOException;
}
