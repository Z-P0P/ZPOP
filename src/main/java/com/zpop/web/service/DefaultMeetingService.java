package com.zpop.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zpop.web.dto.*;
import com.zpop.web.entity.*;
import com.zpop.web.entity.comment.CommentView;

import com.zpop.web.global.exception.CustomException;
import com.zpop.web.global.exception.ExceptionReason;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.zpop.web.dao.AgeRangeDao;
import com.zpop.web.dao.CategoryDao;
import com.zpop.web.dao.CommentDao;
import com.zpop.web.dao.ContactTypeDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MeetingFileDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.NotificationDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dao.RegionDao;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import com.zpop.web.entity.participation.ParticipationInfoView;
import com.zpop.web.utils.ElapsedTimeCalculator;
import com.zpop.web.utils.TextDateTimeCalculator;

@Service
public class DefaultMeetingService implements MeetingService {

	@Autowired
	private MeetingDao dao;
	@Autowired
	private RegionDao regionDao;
	@Autowired
	private ContactTypeDao contactTypeDao;
	@Autowired
	private AgeRangeDao ageRangeDao;

	@Autowired
	private ParticipationDao participationDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MeetingFileDao meetingFileDao;

	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	private CommentDao commentDao;

	public DefaultMeetingService() {
	}

	@Override
	public List<MeetingThumbnailResponse> getList() {
		return getList(0, null, null, null, false);
	}

	@Override
	public List<MeetingThumbnailResponse> getList(String keyword) {
		return getList(0, keyword, null, null, false);
	}

	@Override
	public List<MeetingThumbnailResponse> getList(int startId, String keyword, Integer categoryId, String strRegionIds,
			Boolean isClosed) {

		String[] regionIds = null;
		if (strRegionIds != null)
			regionIds = strRegionIds.split(",");

		MeetingThumbnailPagination pagination = new MeetingThumbnailPagination(
			startId, keyword, categoryId, regionIds, isClosed);

		List<MeetingThumbnailView> meetingThumbnailViews = dao.getThumbnailViewList(pagination);

		// 응답에 맞게 데이터 변환
		List<MeetingThumbnailResponse> list = new ArrayList<>();
		for (MeetingThumbnailView m : meetingThumbnailViews) {
			String genderCategory = "남녀 모두";
			switch (m.getGenderCategory()) {
				case 1:
					genderCategory = "남자 모임";
					break;
				case 2:
					genderCategory = "여자 모임";
					break;
			}

			String dateTime = TextDateTimeCalculator.getTextDateTime(m.getStartedAt());

			// 마감되었거나 시작 일시가 지났다면 isClosed는 true로 응답한다
			boolean isClosedResult = false;
			Date currentTime = new Date();

			if (m.getClosedAt() != null || m.getStartedAt().before(currentTime))
				isClosedResult = true;

			MeetingThumbnailResponse meetingThumbnail = new MeetingThumbnailResponse(m.getId(), m.getCategory(),
				m.getRegion(), m.getAgeRange(), genderCategory, m.getMaxMember(), m.getTitle(), dateTime,
				m.getViewCount(), m.getCommentCount(), isClosedResult);

			list.add(meetingThumbnail);
		}

		return list;
	}

	@Override
	@Transactional
	public RegisterMeetingResponse register(RegisterMeetingRequest dto) throws IOException {

		Meeting meeting = dto.toEntity();
		dao.insert(meeting);
		int meetingId = meeting.getId();

		// 주최자를 참여자로 넣음
		participationDao.insert(meetingId, meeting.getRegMemberId());

		List<MeetingFile> images = dto.getImages();
		images.forEach(image -> image.setMeetingId(meetingId));
		// 업로드한 이미지가 있다면
		if(!images.isEmpty())
			meetingFileDao.updateAllMeetingId(images);
		//
		
		
		/* 모든 image 태그의 data-id = ${image의 id} 추가, 파일 경로에 모임글 id 추가
		Document doc = Jsoup.parse(dto.getContent());
		Elements imageTags = doc.select("img");

		for (int i = 0; i < imageTags.size(); i++) {
			Element tag = imageTags.get(i);
			MeetingFile image = meetingImages.get(i);
			String src = tag.attr("src");
			src = File.separator + "images" + File.separator + String.valueOf(meetingId) + File.separator + src;
			tag.attr("src", src);
			
			tag.attr("data-id", String.valueOf(image.getId()));
		}

		meeting.setContent(doc.body().html());
		
		dao.updateContent(meeting);
		*/
		
		return new RegisterMeetingResponse(meetingId);

	}

	@Override
	@Transactional
	public MeetingDetailResponse getById(int id, Integer memberId) {
		/**
		 * 모임 정보 조회 및 가공
		 */ 
		Meeting meeting = findById(id);
		
		// 성별 변환
		String genderCategory = "남녀 모두";
		switch (meeting.getGenderCategory()) {
			case 1:
				genderCategory = "남자 모임";
				break;
			case 2:
				genderCategory = "여자 모임";
				break;
		}

		// 시작날짜 변환
		String dateTime = TextDateTimeCalculator.getTextDateTime(meeting.getStartedAt());

		// 마감 여부 처리
		boolean isClosed = false;
		if(meeting.getClosedAt() != null)
			isClosed = true;

		// startedAt이 지났다면, 마감처리
		Date startedAt = meeting.getStartedAt();
		if (startedAt.before(new Date())) {
			dao.updateClosedAt(meeting);
			isClosed = true;
		}

		// 내 모임글인지 확인
		boolean isMyMeeting = false;
		if(memberId != null && meeting.getRegMemberId() == memberId)
			isMyMeeting = true;
		
		// 조회수++
		dao.increaseViewCount(id);

		Category category = categoryDao.getById(meeting.getCategoryId());
		AgeRange ageRange = ageRangeDao.get(meeting.getAgeRangeId());
		Region region = regionDao.get(meeting.getRegionId());

		/**
		 * 참가자 정보 조회
		 */ 
		List<ParticipationInfoView> participations = participationDao.getParticipantInfoByMeetingId(id);
		List<ParticipantResponse> participationsResponse = new ArrayList<>();
		boolean hasParticipated = false; 

		for (ParticipationInfoView p : participations) {
			// 취소 or kick 당한 참여자는 스킵
			if(p.getCanceledAt() != null || p.getBannedAt() != null)
				continue;

			// 내가 참여한 모임인지 확인
			if(memberId != null && p.getParticipantId() == memberId)
				hasParticipated = true;

			participationsResponse.add(new ParticipantResponse(
				p.getId(),
				p.getParticipantId(),
				p.getNickname(),
				p.getProfileImagePath()
			));
		}

		/**
		 * 댓글 정보 조회 및 가공
		 */ 
		List<CommentView> comments = commentDao.getComment(id);

		List<CommentResponse> commentsResponse = new ArrayList<>();

		for(CommentView c:comments) {
			//본인댓글인지 여부표시
			boolean isMyComment = false;
			if(memberId != null && c.getWriterId() == memberId)
				isMyComment = true;

			commentsResponse.add(
				new CommentResponse(
					c.getId(),
					c.getMeetingId(),
					c.getWriterId(),
					c.getNickname(),
					c.getContent(),
					c.getProfileImgPath(),
					c.getParentCommentId(),
					c.getGroupId(),
					c.getCreatedAt(),
					c.getUpdatedAt(),
					c.getDeletedAt(),
					c.getParentMemberId(),
					c.getParentNickname(),
					c.getParentImg(),
					ElapsedTimeCalculator.getElpasedTime(c.getCreatedAt()),
					commentDao.getCountOfReply(c.getId()), // TODO: 답글 수 컬럼 추가하기
					isMyComment
			));
		}	 

		return new MeetingDetailResponse(
			meeting.getId(),
			meeting.getTitle(),
			meeting.getStartedAt(),
			dateTime,
			meeting.getDetailRegion(),
			meeting.getContent(),
			category.getName(),
			region.getName(),
			meeting.getMaxMember(),
			genderCategory,
			ageRange.getType(),
			meeting.getRegMemberId(),
			meeting.getViewCount(),
			meeting.getCommentCount(),
			isMyMeeting,
			hasParticipated,
			isClosed,
			participationsResponse,
			commentsResponse
		);
	}

	@Override
	public String getContact(int id, int memberId) {
		Meeting foundMeeting = findById(id);
		// 참여자가 맞는지 확인한다
		findParticipationByMeetingAndMember(id, memberId);
		return foundMeeting.getContact();
	}

	@Override
	public boolean delete(int id, int memberId) {

		Meeting foundMeeting = findById(id);

		if (foundMeeting.getRegMemberId() != memberId)
			throw new CustomException(ExceptionReason.AUTHORIZATION_ERROR);

		List<Participation> participations = participationDao.getListByMeetingId(id);

		for (Participation p : participations) {
			// 주최자 자기 자신 제외
			if (p.getParticipantId() == memberId)
				continue;

			Date bannedAt = p.getBannedAt();
			Date canceledAt = p.getCanceledAt();

			// 정상 참가자가 한명이라도 있으면 삭제 불가
			if (bannedAt == null && canceledAt == null)
				throw new CustomException(ExceptionReason.PARTICIPANTS_EXISTS);
		}
		dao.updateDeletedAt(foundMeeting);

		return true;
	}

	@Override
	public boolean kick(int id, int hostId, int participantId) {
		Meeting foundMeeting = findById(id);

		if (foundMeeting.getRegMemberId() != hostId)
			throw new CustomException(ExceptionReason.AUTHORIZATION_ERROR);

		// 모임의 참여자들 중, participantId가 있는지 확인
		List<Participation> participations = participationDao.getListByMeetingId(id);
		Participation kickTarget = null;
		for (Participation p : participations) {
			if (p.getParticipantId() == participantId) {
				kickTarget = p;
				break;
			}
		}

		if (kickTarget == null || kickTarget.getCanceledAt() != null)
			new CustomException(ExceptionReason.NOT_FOUND_MEMBER);
		if (kickTarget.getBannedAt() != null)
			new CustomException(ExceptionReason.ALREADY_KICKED);
		// 자기자신을 강퇴하려 할 때
		if (kickTarget.getParticipantId() == hostId)
			new CustomException(ExceptionReason.VALIDATION_ERROR);

		participationDao.updateBannedAt(participantId);

		return true;
	}

	@Override
	public RegisterMeetingViewResponse getActiveOptions() {

		List<RegionDto> regions = regionDao.getActiveList();
		List<CategoryDto> categories = categoryDao.getActiveList();
		List<ContactTypeDto> contactTypes = contactTypeDao.getActiveList();
		List<AgeRangeDto> ageRanges = ageRangeDao.getActiveList();

		return new RegisterMeetingViewResponse(regions, categories, contactTypes, ageRanges);
	}

	@Override
	public boolean close(int id, Member member) {

		Meeting foundMeeting = findById(id);

		int memberId = member.getId();

		if (foundMeeting.getRegMemberId() != memberId)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		if (foundMeeting.getClosedAt() != null)
			throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 마감된 모임입니다");

		// 모임 시작일이 지났을 때 -> 마감 후 예외
		Date startedAt = foundMeeting.getStartedAt();
		if (startedAt.before(new Date())) {
			dao.updateClosedAt(foundMeeting);
			throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 마감된 모임입니다");
		}

		dao.updateClosedAt(foundMeeting);

		return true;
	}
	@Override
	@Transactional
	public ParticipationResponse participate(int id, int memberId) {

		Meeting foundMeeting = findById(id);

		// 주최자가 자기 자신 모임에 참여할 때
		if (foundMeeting.getRegMemberId() == memberId)
			throw new CustomException(ExceptionReason.ALREADY_PARTICIPATED);

		boolean isClosedMeeting = isClosedMeeting(foundMeeting);
		if(isClosedMeeting)
			throw new CustomException(ExceptionReason.CLOSED_MEETING);

		List<Participation> participants = participationDao.getListByMeetingId(id);
		for(Participation p : participants) {
			if(p.getParticipantId() != memberId)
				continue;
			if(p.getBannedAt() != null)
				throw new CustomException(ExceptionReason.KICKED_MEMBER);
			if(p.getParticipantId() == memberId && p.getCanceledAt() == null)
				throw new CustomException(ExceptionReason.ALREADY_PARTICIPATED);
		}

		int maxMember = foundMeeting.getMaxMember();
		int hostId = foundMeeting.getRegMemberId();
		int participantCount = participationDao.countActiveByMeetingId(id);

		// 현재 참여자 수 & 최대인원 비교 확인
		if(participantCount >= maxMember)
			throw new CustomException(ExceptionReason.CLOSED_MEETING);

		// 참여 성공
		participationDao.insert(id, memberId);
		boolean isClosed = false;
		// 참여 처리 후 참여자 수에 따른 마감 처리
		int resultCount = participationDao.countActiveByMeetingId(id);
		if(resultCount >= maxMember) {
			dao.updateClosedAt(foundMeeting);
			isClosed = true;
		}

		createNotification(hostId, "/meeting/"+ id,2);

		return new ParticipationResponse(foundMeeting.getContact(), isClosed);
	}

	@Override
	@Transactional
	public boolean updateMeeting(UpdateMeetingRequest dto) throws IOException {

		int meetingId = dto.getMeetingId();
		int regMemberId = dto.getRegMemberId();

		Meeting meeting = findById(meetingId);

		if (meeting.getRegMemberId() != regMemberId) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "권한이 없습니다.");
		}

		// 수정된 내용 update
		dao.update(dto.toEntity());
		
		// 가지고온 image에 모임 id를 넣어줌 
		List<MeetingFile> images = dto.getImages();
		images.forEach(image->image.setMeetingId(meetingId));
		
		// 가지고온 image를 제외한 나머지를 제거함
		meetingFileDao.deleteAllExcept(images);
		
		// 가지고온 image를 업데이트 함
		if (!images.isEmpty()) {
			meetingFileDao.updateAllMeetingId(images);			
		}
		return true;	
	}

	@Override
	public UpdateMeetingViewDto getUpdateMeetingView(int id, int memberId) {
		UpdateMeetingViewDto updateView = dao.getUpdateView(id);
		if(updateView == null)
			throw new CustomException(ExceptionReason.NOT_FOUND_MEETING);
		if(memberId != updateView.getRegMemberId())
			throw new CustomException(ExceptionReason.AUTHORIZATION_ERROR);
		return updateView;
	}

	@Override
	public MeetingFile uploadFile(MultipartFile file, String path) throws IOException {

		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}

		String completePath = path + File.separator + file.getOriginalFilename();
		
		InputStream fis = file.getInputStream();
		OutputStream fos = new FileOutputStream(completePath);

		byte[] buf = new byte[1024];
		int size = 0;
		while ((size = fis.read(buf)) > 0) {
			fos.write(buf, 0, size);
		}

		fos.close();
		fis.close();
		MeetingFile meetingFile = new MeetingFile(file.getOriginalFilename());
		meetingFileDao.insert(meetingFile);
		return meetingFile;
	}


	@Override
	public boolean cancelParticipate(int id, int memberId) {

        // 모임이 존재하는지 확인한다
        Meeting foundMeeting = findById(id);

        Participation participationInfo = findParticipationByMeetingAndMember(id, memberId);

        /*
         * 1. 취소한 모임에 참여 취소할 때
         * 2. kick당한 모임에 참여 취소할 때
         */
        if( participationInfo.getCanceledAt() != null ||
            participationInfo.getBannedAt() != null )
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "참여한 모임에만 참여를 취소할 수 있습니다");

        // 마감된 모임은 참여를 취소할 수 없다
		boolean isClosedMeeting = isClosedMeeting(foundMeeting);
		if(isClosedMeeting)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "마감된 모임에 참여를 취소할 수 없습니다");
        participationDao.updateCanceledAt(participationInfo.getId());

        return true;
	}

	/**
	 *  모임의 closedAt || startedAt을 확인하여 마감된 모임인지 확인한다.
	 */
	private boolean isClosedMeeting(Meeting meeting) {
		Date currentTime = new Date();
		Date meetingStartedAt = meeting.getStartedAt();

		if(meeting.getClosedAt() != null ||
				currentTime.after(meetingStartedAt))
			return true;

		return false;
	}

	@Override
	public List<ParticipantResponse> getParticipants(int id) {
		findById(id);

		List<ParticipationInfoView> list = participationDao.getParticipantInfoByMeetingId(id);
		List<ParticipantResponse> participants = new ArrayList<>();

		for (ParticipationInfoView p : list) {
			// 취소 or 내보낸 당한 참여자는 스킵
			if(p.getCanceledAt() != null || p.getBannedAt() !=null)
				continue;

			ParticipantResponse participant =
				new ParticipantResponse(p.getId(), p.getParticipantId(), p.getNickname(), p.getProfileImagePath());

			participants.add(participant);
		}

		return participants;
	}

	private void createNotification(int memberId, String url, int type) {
		notificationDao.insertNotification(memberId, url, type);
	}

	private Meeting findById(int id) {
		Meeting meeting = dao.get(id);
		if(meeting == null || meeting.getDeletedAt() != null)
			throw new CustomException(ExceptionReason.NOT_FOUND_MEETING);
		return meeting;
	}

	private Participation findParticipationByMeetingAndMember(int meetingId, int memberId) {
		Participation participationInfo = null;
		List<Participation> participations = participationDao.getListByMeetingId(meetingId);
		for(Participation p : participations) {
			if(p.getParticipantId() == memberId) {
				participationInfo = p;
				break;
			}
		}
		if(participationInfo == null)
			throw new CustomException(ExceptionReason.NOT_JOIN_MEETING);
		return participationInfo;
	}
}