package com.zpop.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.zpop.web.dao.ContactTypeDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MeetingFileDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.dao.RegionDao;
import com.zpop.web.dto.AgeRangeDto;
import com.zpop.web.dto.CategoryDto;
import com.zpop.web.dto.ContactTypeDto;
import com.zpop.web.dto.MeetingDetailDto;
import com.zpop.web.dto.MeetingParticipantsDto;
import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.dto.MeetingThumbnailResponse;
import com.zpop.web.dto.RegionDto;
import com.zpop.web.dto.RegisterMeetingRequest;
import com.zpop.web.dto.RegisterMeetingResponse;
import com.zpop.web.dto.RegisterMeetingViewResponse;
import com.zpop.web.dto.UpdateMeetingRequest;
import com.zpop.web.dto.UpdateMeetingViewDto;
import com.zpop.web.entity.MeetingFile;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import com.zpop.web.utils.TextDateTimeCalculator;

import jakarta.validation.Valid;

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
	@Transactional
	public RegisterMeetingResponse register(RegisterMeetingRequest dto) throws IOException {

		Meeting meeting = dto.toEntity();
		dao.insert(meeting);
		int meetingId = meeting.getId();

		// 주최자를 참여자로 넣음
		participationDao.insert(meetingId, meeting.getRegMemberId());

		List<MeetingFile> images = dto.getImages();
		images.forEach(image -> image.setMeetingId(meetingId));
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
			System.out.println(image.toString());
			tag.attr("data-id", String.valueOf(image.getId()));
		}

		meeting.setContent(doc.body().html());
		System.out.println(doc.body().html());
		dao.updateContent(meeting);
		*/
		
		return new RegisterMeetingResponse(meetingId);

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
	public RegisterMeetingViewResponse getActiveOptions() {

		List<RegionDto> regions = regionDao.getActiveList();
		List<CategoryDto> categories = categoryDao.getActiveList();
		List<ContactTypeDto> contactTypes = contactTypeDao.getActiveList();
		List<AgeRangeDto> ageRanges = ageRangeDao.getActiveList();

		return new RegisterMeetingViewResponse(regions, categories, contactTypes, ageRanges);
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

	@Override
	public int participate(int meetingId, int memberId) {
		// 주최자가 참여한 경우 -> host ID랑 MemberId랑 같을 경우
		// 참여하기를 눌렀는데 모임의 아이디가 없을 경우
		// 강퇴당한 사용자일 경우
		// 마감된 모임일 경우
//				Participation participation = new Participation(participation);

		int maxNumber = dao.getMaxMember(meetingId);
		System.out.println(maxNumber);
		return participationDao.insert(meetingId, memberId);
	}

	@Override
	@Transactional
	public boolean updateMeeting(UpdateMeetingRequest dto) throws IOException {

		int meetingId = dto.getMeetingId();
		int regMemberId = dto.getRegMemberId();

		Meeting meeting = dao.get(meetingId);
		if (meeting == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "모임글이 없습니다.");
		}

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
	public UpdateMeetingViewDto getUpdateMeetingView(int id) {
		return dao.getUpdateView(id);
	}

	@Override
	public MeetingFile uploadFile(MultipartFile file, String path) throws IOException {

		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}

		String completePath = path + File.separator + file.getOriginalFilename();
		System.out.println(completePath);
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

}
