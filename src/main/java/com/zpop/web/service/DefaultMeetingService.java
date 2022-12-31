package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import com.zpop.web.utils.TextDateTimeCalculator;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zpop.web.dao.CategoryDao;

import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;

@Service
public class DefaultMeetingService implements MeetingService {

	@Autowired
	private MeetingDao dao;

	@Autowired
	private ParticipationDao participationDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private MemberDao memberDao;

	@Autowired
	private MemberService memberService;
	
	public DefaultMeetingService() {
	}

	public DefaultMeetingService(MeetingDao dao, ParticipationDao participationDao) {
		this.dao = dao;
		this.participationDao = participationDao;

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

		MeetingThumbnailPagination pagination = new MeetingThumbnailPagination(startId, keyword, categoryId, regionIds,
				isClosed);

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

			boolean isClosedResult = false;
			if (m.getClosedAt() != null)
				isClosedResult = true;

			MeetingThumbnailResponse meetingThumbnail = new MeetingThumbnailResponse(m.getId(), m.getCategory(),
					m.getRegion(), m.getAgeRange(), genderCategory, m.getMaxMember(), m.getTitle(), dateTime,
					m.getViewCount(), m.getCommentCount(), isClosedResult);

			list.add(meetingThumbnail);
		}

		return list;
	}

	@Override
	public int register(Meeting meeting) {
		return dao.insert(meeting);
	}

//	public int participate(Participation participation) {
//		// 주최자가 참여한 경우 -> host ID랑 MemberId랑 같을 경우
//		// 참여하기를 눌렀는데 모임의 아이디가 없을 경우
//		// 강퇴당한 사용자일 경우
//		// 마감된 모임일 경우
//		//		Participation participation = new Participation(participation);
//		return participationDao.insert(participation);
//	}

	@Override
	public MeetingDetailDto getById(int id) {
		return dao.getDetailView(id);
	}

	@Override
	public List<MeetingParticipantsDto> getParticipants(int meetingId) {

		return participationDao.getByMeetingId(meetingId);
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

		// 탈퇴한 회원인지 확인
		int kickTargetMemberId = kickTarget.getParticipantId();
		Member kickTargetMember = memberDao.getById(kickTargetMemberId);
		if (kickTargetMember.getResignedAt() != null) {
			// TODO: dao.참여취소(kickTargetId);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다");
		}

		// 자기자신을 강퇴하려 할 때
		if (kickTargetMemberId == member.getId())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자기 자신을 내보낼 수 없습니다");

		participationDao.updateBannedAt(kickTarget.getId());

		return true;
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
		int count = participationDao.getparticipantsCount(meetingId);
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
			result = 1;
		}
		else
			participationDao.insert(meetingId, memberId);
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


}
