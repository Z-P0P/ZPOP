package com.zpop.web.service;

import com.zpop.web.dto.*;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.MeetingFile;

import com.zpop.web.entity.Participation;

import com.zpop.web.entity.meeting.Meeting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public interface MeetingService {

	/**
	 * 모임 리스트 기본 페이지.
	 * 
	 * @return 모임 썸네일 리스트
	 */
	List<MeetingThumbnailResponse> getList();
	/**
	 * 모임 리스트 모바일 조회 페이지.
	 * 
	 * @param keyword 검색어
	 * @return 모임 썸네일 리스트
	 */
	List<MeetingThumbnailResponse> getList(String keyword);
	/**
	 * 모임 리스트를 옵션을 포함하여 조회하기.
	 * 
	 * {@link String[] 지역아이디}는 쿼리스트링에 `지역1,지역2`처럼 다수로 값이 온다.
	 * 성별은 DB에 정수이므로 변환하여 응답한다.
	 * 시작일은 n분전, n일전처럼 표기를 변환하여 응답한다.
	 * 마감 되었거나, 모임 시작일시가 지났다면 isClosedResult true로 응답한다.
	 * 
	 * @param startId 모임 리스트 조회를 시작하는 id
	 * @param keyword 검색어
	 * @param categoryId 카테고리 id
	 * @param regionIds 지역 id들
	 * @param isClosed 모임 마감 여부
	 * @return
	 */
	List<MeetingThumbnailResponse> getList(int startId, String keyword, Integer categoryId, String regionIds, Boolean isClosed);

	/**
	 * 모임을 상세 조회하기.
	 * 
	 * TODO 작성 필요
	 * 
	 * @param id 모임 아이디
	 * @return {@link MeetingDetailDto}
	 */
	MeetingDetailResponse getById(int id, Integer memberId);

	/**
	 * 참여자 목록 조회하기.
	 *
	 * 해당 id의 모임의 참여자 목록을 조회한다.
	 * 존재하지 않는 id의 모임이면 조회할 수 없다.
	 *
	 * @param id
	 * @return 참여자 목록
	 */
	List<ParticipantResponse> getParticipants(int id);

	/**
	 * 모임 연락처 얻기.
	 *
	 * 참여자가 해당 id의 모임에 대해 연락처를 얻는다.
	 * 만약 존재하지 않는 id의 모임이거나, 모임이 이미 삭제 되었다면, 연락처를 얻을 수 없다.
	 * memberId가 {@link Participation 참여자}인지 확인 후, 모임에 참여한 적이 없다면 연락처를 얻을 수 없다.
	 *
	 * @param id 모임 아이디
	 * @param memberId 멤버 아이디
	 * @return 연락처
	 */
	String getContact(int id, int memberId);

	/**
     * 모임에 참여하기.
     * <p>
     * 멤버가 해당 id의 모임에 대해 참여한다.
     * 만약 존재하지 않는 id의 모임이거나, 모임이 이미 삭제 되었다면 참여할 수 없다.
     * 마감된 모임에 참여할 수 없다.
     * kick 당한 멤버 id는 모임에 참여할 수 없다.
     * 이미 참여한 {@link Participation 참여자}인지 확인 후, 이미 참여했다면 참여할 수 없다.
     * 멤버 id가 주최자 id와 같다면, 참여할 수 없다.
     * 참여 완료후 참여자수가 최대 인원수와 같거나 큰 경우, 모임을 마감 처리한다.
     *
     * @param id       모임 아이디
     * @param memberId 멤버 아이디
     * @return ParticipationResponse 연락처, 마감여부
     */
	ParticipationResponse participate(int id, int memberId);

	/**
	 * 모임 참여를 취소하기.
	 * 
	 * 참여자가 해당 id의 모임에 대해 참여를 취소한다.
	 * 만약 존재하지 않는 id의 모임이거나, 모임이 이미 삭제 되었다면, 예외를 던진다.
	 * 또 마감된 모임에 대해서도 참여를 취소할 수 없다.
	 * memberId가 {@link Participation 참여자}인지 확인 후, 모임에 참여한 적이 없다면
	 * 예외를 던진다.
	 * 이미 취소한 모임, 강퇴당한 모임일 경우 예외를 던진다.
	 * 
	 * @param id 모임 아이디
	 * @param memberId 멤버 아이디
	 * @return 모임 연락처
	 */
	boolean cancelParticipate(int id, int memberId);


	/**
	 * 참여자를 내보내기.
	 * 
	 * 해당 participantId의 참여자를 주최자가 내보낸다.
	 * 만약 존재하지 않는 id의 모임이거나, 모임이 이미 삭제 되었다면, 예외를 던진다.
	 * {@link Meeting 모임}의 id와 주최자의 id가 일치하지 않는다면, 내보낼 수 없다.
	 * {@link Participation 참여자}들 중, participantId가 존재하지 않는다면, 예외를 던진다.
	 * {@link Participation 참여자}가 이미 내보내졌다면, 예외를 던진다.
	 * {@link Member 참여자}가 만약 탈퇴 회원이라면, 참여 취소 처리후 예외를 던진다.
	 * 또 자기자신을 내보낼 수 없다.
	 * 
	 * @param id 모임 아이디
	 * @param participantId 참여자 아이디
	 * @param hostId 주최자 아이디  
	 * @return 성공 여부
	 */
	boolean kick(int id, int participantId, Member member); //TODO: 시큐리티

	/**
	 * 모임을 마감하기.
	 * 
	 * 해당 id의 모임을 주최자가 마감한다.
	 * 만약 존재하지 않는 id의 모임이거나, 모임이 이미 삭제 되었다면, 마감할 수 없다.
	 * {@link Meeting 모임}의 id와 주최자의 id가 일치하지 않는다면, 마감할 수 없다.
	 * {@link Meeting 모임}이 이미 마감되었다면, 마감할 수 없다.
	 * 만약 모임시작일이 지났다면, 마감 처리 후 사용자에게 마감 할 수 없다고 알린다.
	 * 
	 * @param id 모임 아이디
	 * @param hostId 주최자 아이디
	 * @return 성공 여부
	 */
	boolean close(int id, Member member);  //TODO: 시큐리티

	/**
	 * 모임 삭제하기.
	 * 
	 * 해당 id의 모임을 삭제한다.
	 * 만약 존재하지 않는 id의 모임이거나, 모임이 이미 삭제 되었다면, 삭제할 수 없다.
	 * {@link Meeting 모임}의 id와 주최자의 id가 일치하지 않는다면, 삭제할 수 없다.
	 * 모임에 참여자가 자신을 제외한 한명이라도 있다면, 삭제할 수 없다.
	 * 
	 * @param id 모임 아이디
	 * @param hostId 주최자 아이디
	 * @return 성공 여부
	 */
	boolean delete(int id, Member member);  //TODO: 시큐리티

	RegisterMeetingViewResponse getActiveOptions();

	RegisterMeetingResponse register(RegisterMeetingRequest dto) throws FileNotFoundException, IOException;
			
	boolean updateMeeting(UpdateMeetingRequest dto) throws IOException;
			
	UpdateMeetingViewDto getUpdateMeetingView(int id);
			
	MeetingFile uploadFile(MultipartFile file, String path) throws IOException;

}
