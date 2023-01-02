package com.zpop.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zpop.web.dto.*;
import com.zpop.web.entity.*;
import com.zpop.web.entity.comment.CommentView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
			String genderCategory = "누구나";
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
	public int register(RegisterMeetingRequest dto, List<MultipartFile> images, String realPath) throws IOException {

		if (dto.getCategoryId() == 0) {
			// 카테고리 입력 x
		}
		if (dto.getRegionId() == 0) {
			// 지역 입력 x
		}

		if (dto.getAgeRangeId() == 0) {
			// 연령 입력 x
		}

		if (dto.getContactTypeId() == 0) {
			// 연락방법 입력 x
		}

		if (dto.getGenderCategory() == 0) {
			// 성별 입력 x
		}

		if (dto.getTitle().equals("")) {
			// 제목 입력 x
		}

		if (dto.getContent().equals("")) {
			// 내용 입력 x
		}

		if (dto.getMaxMember() < 2) {
			// 인원 미입력
		}

		if (dto.getStartedAt() == null) {
			// 시작시간 입력 x
		}

		if (dto.getStartedAt().before(new Date())) {
			// 현재시간보다 과거 시간을 입력
		}

		if (dto.getDetailRegion().equals("")) {
			// 상세 지역 미입력
		}

		if (dto.getContact().equals("")) {
			// 연락처 미입력
		}

		Meeting meeting = dto.toEntity();
		dao.insert(meeting);
		int meetingId = meeting.getId();
		System.out.println(meetingId + " " + meeting.getRegMemberId());
		int participation = participationDao.insert(meetingId, meeting.getRegMemberId());

		realPath += File.separator + String.valueOf(meetingId);

		Document doc = Jsoup.parse(dto.getContent());
		Elements imageTags = doc.select("img");
		for (Element tag : imageTags) {
			String src = tag.attr("src");
			src = File.separator + "images" + File.separator + String.valueOf(meetingId) + File.separator
					+ src;
			tag.attr("src", src);
		}

		if (images != null) {
			for (MultipartFile image : images) {

				File pathFile = new File(realPath);
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}

				String completePath = realPath + File.separator + image.getOriginalFilename();
				System.out.println(completePath);
				InputStream fis = image.getInputStream();
				OutputStream fos = new FileOutputStream(completePath);

				byte[] buf = new byte[1024];
				int size = 0;
				while ((size = fis.read(buf)) > 0) {
					fos.write(buf, 0, size);
				}

				fos.close();
				fis.close();
			}
			meeting.setContent(doc.body().html());
			System.out.println(doc.body().html());
			dao.updateContent(meeting);
		}

		return 1;
	}

	@Override
	@Transactional
	public MeetingDetailResponse getById(int id, Integer memberId) {
		/**
		 * 모임 정보 조회 및 가공
		 */ 
		Meeting meeting = dao.get(id);

		if(meeting == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");
		
		// 성별 변환
		String genderCategory = "누구나";
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

		for (ParticipationInfoView p : participations) {
			participationsResponse.add(new ParticipantResponse(
				p.getId(),
				p.getNickname(),
				p.getProfileImagePath()
			));
		}

		/**
		 * 댓글 정보 조회 및 가공
		 */ 
		List<CommentView> comments = commentDao.getComment(id);
		int commentCount = commentDao.getCountOfComment(id);

		List<CommentResponse> commentsResponse = new ArrayList<>();

		for(CommentView c:comments) {
			//본인댓글인지 여부표시
			boolean isMyComment = false;
			if(memberId != null && c.getWriterId() == memberId)
				isMyComment = true;
			System.out.println(c.getId());
			System.out.println(c.getId() + " is " + commentDao.getCountOfReply(c.getGroupId()));
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
			commentCount,
			isMyMeeting,
			isClosed,
			participationsResponse,
			commentsResponse
		);
	}

	@Override
	public String getContact(int id, int memberId) {
		Meeting foundMeeting = dao.get(id);

		if (foundMeeting == null || foundMeeting.getDeletedAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");

		// 참여자가 맞는지 확인한다
		Participation participationInfo = null;

		List<Participation> participations = participationDao.getListByMeetingId(id);
		for(Participation p : participations) {
			if(p.getParticipantId() == memberId) {
				participationInfo = p;
				break;
			}
		}

		if(participationInfo == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "참여하지않은 모임입니다");

		return foundMeeting.getContact();
	}

	@Override
	public boolean delete(int id, Member member) {

		Meeting foundMeeting = dao.get(id);

		if (foundMeeting == null || foundMeeting.getDeletedAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");

		int memberId = member.getId();

		if (foundMeeting.getRegMemberId() != memberId)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		List<Participation> participations = participationDao.getListByMeetingId(id);

		for (Participation p : participations) {
			// 주최자 자기 자신 제외
			if (p.getParticipantId() == memberId)
				continue;

			Date bannedAt = p.getBannedAt();
			Date canceledAt = p.getCanceledAt();

			// 정상 참가자가 한명이라도 있으면 삭제 불가
			if (bannedAt == null && canceledAt == null)
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "참가자가 있어 모임을 삭제할 수 없습니다");
		}
		dao.updateDeletedAt(foundMeeting);

		return true;
	}

	@Override
	public void updateViewCount(int id) {
		dao.updateViewCount(id);

	}

	@Override
	public boolean kick(int id, int participantId, Member member) {
		Meeting foundMeeting = dao.get(id);

		if (foundMeeting == null || foundMeeting.getDeletedAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");

		int memberId = member.getId();

		if (foundMeeting.getRegMemberId() != memberId)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		List<Participation> participations = participationDao.getListByMeetingId(id);

		Participation kickTarget = null;

		for (Participation p : participations) {
			if (p.getParticipantId() == participantId) {
				kickTarget = p;
				break;
			}
		}

		if (kickTarget == null || kickTarget.getCanceledAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "모임에 참여하지 않는 회원입니다");

		if (kickTarget.getBannedAt() != null)
			throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 강퇴된 회원입니다");

		// 탈퇴한 회원인지 확인 후
		// 탈퇴한 회원이라면 참여 취소처리한다.
		int kickTargetMemberId = kickTarget.getParticipantId();
		Member kickTargetMember = memberDao.getById(kickTargetMemberId);
		if (kickTargetMember.getResignedAt() != null) {
			participationDao.updateCanceledAt(kickTargetMemberId);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다");
		}

		// 자기자신을 강퇴하려 할 때
		if (kickTargetMemberId == member.getId())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 내보낼 수 없습니다");

		participationDao.updateBannedAt(kickTarget.getId());

		return true;
	}

	@Override
	public RegisterMeetingResponse getActiveOptions() {

		List<RegionDto> regions = regionDao.getActiveList();
		List<CategoryDto> categories = categoryDao.getActiveList();
		List<ContactTypeDto> contactTypes = contactTypeDao.getActiveList();
		List<AgeRangeDto> ageRanges = ageRangeDao.getActiveList();

		return new RegisterMeetingResponse(regions, categories, contactTypes, ageRanges);
	}

	@Override
	public boolean close(int id, Member member) {

		Meeting foundMeeting = dao.get(id);

		if (foundMeeting == null || foundMeeting.getDeletedAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");

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
	
	/************************* 참여 관련 로직 **********************/
	// 주최자가 참여한 경우 -> host ID랑 MemberId랑 같을 경우
	// 참여하기를 눌렀는데 모임의 아이디가 없을 경우
	// 강퇴당한 사용자일 경우
	// 마감된 모임일 경우
//		3. 로그인을 하지 않은 사용자가 참여하기 버튼을 누른 경우 -> 로그인 모달이 나와야됨.
//		4. 내가 이미 참여한 모임일 경우
	public static boolean isMemberParticipated(int memberId, List<Participation> participants) {
        for(Participation p : participants) {
           if(p.getParticipantId()== memberId) {
              return true;
           }
        }
        return false;
     }
	@Override
	public int participate(int meetingId, int memberId) {
		
		List<Participation> participants = participationDao.getListByMeetingId(meetingId);
		int maxMember = dao.getmaxMember(meetingId);
		int count = participationDao.countByMeetingId(meetingId);
		int hostId = dao.getMeetingHost(meetingId);
		int result = 0;
		// result는 성공(1) 실패(0)
		
		// for each에서 앞에 오는것은 무조건 타입
		// Java List에서 null체크를 할 때는 isEmpty()를 사용한다.
		if (!participants.isEmpty()) {//개발중엔 임시로 null check필요
			if(maxMember <= count)
				return 0;
			if(isMemberParticipated (memberId,participants)) {
				return 0;
			}
			participationDao.insert(meetingId, memberId);
			createNotification(hostId, "/meeting/"+meetingId,2);
			result = 1;
		}
		else {
			participationDao.insert(meetingId, memberId);
			createNotification(hostId, "/meeting/"+meetingId,2);
			result = 1;
		}	
		return result;
	}
	@Override
	public int getUserType(int memberId, int meetingId) {
		List<Participation> participants = participationDao.getListByMeetingId(meetingId);
		int hostId = dao.getMeetingHost(meetingId);
		int userType = 0;
		// userType
		// 0--> 일반(비로그인)
		// 1 --> 일반(로그인)
		// 2--> 참여자 
		// 3--> 호스트
		if(memberId == hostId) {
			userType = 3;
		}
		else if(isMemberParticipated (memberId,participants)){
			userType = 2;
		}
		else if(memberId != 0) {
			userType = 1;
		}
		else {
			userType = 0;
		}
		return userType;
	}
	
	@Override
	public boolean cancelParticipate(int id, int memberId) {

        // 모임이 존재하는지 확인한다
        Meeting foundMeeting = dao.get(id);

        if(foundMeeting == null || foundMeeting.getDeletedAt() != null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 모임입니다");
        
        // 참여자가 맞는지 확인한다
        Participation participationInfo = null;

        List<Participation> participations = participationDao.getListByMeetingId(id);
        for(Participation p : participations) {
            if(p.getParticipantId() == memberId) {
                participationInfo = p;
                break;
            }
        }

        /*
         * 1. 모임에 참여한 적이 없을 때
         * 2. 취소한 모임에 참여 취소할 때
         * 3. kick당한 모임에 참여 취소할 때
         */
        if(participationInfo == null ||
            participationInfo.getCanceledAt() != null ||
            participationInfo.getBannedAt() != null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "참여한 모임에만 참여를 취소할 수 있습니다");

        // 마감된 모임은 참여를 취소할 수 없다
        Date currentTime = new Date();
        Date meetingStartedAt = foundMeeting.getStartedAt();

        if(foundMeeting.getClosedAt() != null ||
            currentTime.after(meetingStartedAt))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "마감된 모임에 참여를 취소할 수 없습니다");
        
        //participationDao.updateCanceledAt(participationInfo.getId());

        return true;
    }

	private void createNotification(int memberId, String url, int type) {
		notificationDao.insertCommentNotification(memberId, url, type);
	}

	
    
}