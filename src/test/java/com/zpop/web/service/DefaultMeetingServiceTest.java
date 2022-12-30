package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.MemberDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.Participation;
import com.zpop.web.entity.meeting.Meeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DefaultMeetingServiceTest {

    @InjectMocks
    private MeetingService service = new DefaultMeetingService();

    @Mock
    private MeetingDao dao;

    @Mock
    private ParticipationDao participationDao;

    @Mock
    private MemberDao memberDao;

    // 모임 참여 취소하기 --------------------\
    @Test
    public void cancelParticipate_모임에_참여를_취소한다() throws ParseException {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();
        member.setId(memberId);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        Date futureTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .parse("9999-01-01 11:11:11");
        meeting.setStartedAt(futureTime);

        Participation participation = new Participation();
        participation.setParticipantId(memberId);
        List<Participation> list = new ArrayList<>();
        list.add(participation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        boolean result = service.cancelParticipate(meetingId, memberId);

        //then
        assertThat(result).isEqualTo(true);

        // verify
        verify(participationDao, times(1)).updateCanceledAt(participation.getId());
    }

    @Test
    public void cancelParticipate_존해하지_않는_모임이라면_NOTFOUND() {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();

        // mocking
        given(dao.get(anyInt())).willReturn(null);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.cancelParticipate(meetingId, memberId));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(e.getReason()).isEqualTo("존재하지 않는 모임입니다");
    }

    @Test
    public void cancelParticipate_참여하지_않은_모임_BAD_REQUEST() {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();
        member.setId(memberId);

        Meeting meeting = new Meeting();
        meeting.setId(1);

        Participation otherParticipation = new Participation();
        List<Participation> list = new ArrayList<>();
        list.add(otherParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.cancelParticipate(meetingId, memberId));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getReason()).isEqualTo("참여한 모임에만 참여를 취소할 수 있습니다");
    }

    @Test
    public void cancelParticipate_이미_참여_취소한_모임_BAD_REQUEST() {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();
        member.setId(memberId);

        Meeting meeting = new Meeting();
        meeting.setId(1);

        Participation alreadyCanceledParticipation = new Participation();
        alreadyCanceledParticipation.setCanceledAt(new Date());
        List<Participation> list = new ArrayList<>();
        list.add(alreadyCanceledParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.cancelParticipate(meetingId, memberId));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getReason()).isEqualTo("참여한 모임에만 참여를 취소할 수 있습니다");
    }

    @Test
    public void cancelParticipate_이미_kick당한_모임_BAD_REQUEST() {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();
        member.setId(memberId);

        Meeting meeting = new Meeting();
        meeting.setId(1);

        Participation alreadyKickedParticipation = new Participation();
        alreadyKickedParticipation.setBannedAt(new Date());
        List<Participation> list = new ArrayList<>();
        list.add(alreadyKickedParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.cancelParticipate(meetingId, memberId));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getReason()).isEqualTo("참여한 모임에만 참여를 취소할 수 있습니다");
        assertNotNull(list.get(0).getBannedAt());
    }

    @Test
    public void cancelParticipate_마감된_모임에_참여_취소는_BAD_REQUEST_1() throws ParseException {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();
        member.setId(memberId);

        Meeting closedMeeting = new Meeting();
        closedMeeting.setId(1);
        closedMeeting.setClosedAt(new Date());

        Participation participation = new Participation();
        participation.setParticipantId(memberId);
        List<Participation> list = new ArrayList<>();
        list.add(participation);

        // mocking
        given(dao.get(anyInt())).willReturn(closedMeeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.cancelParticipate(meetingId, memberId));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getReason()).isEqualTo("마감된 모임에 참여를 취소할 수 없습니다");
    }

    @Test
    public void cancelParticipate_마감된_모임에_참여_취소는_BAD_REQUEST_2() throws ParseException {
        //given
        int meetingId = 1;
        int memberId = 1;
        Member member = new Member();
        member.setId(memberId);

        Meeting closedMeeting = new Meeting();
        closedMeeting.setId(1);
        Date pastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .parse("1999-01-01 11:11:11");
        closedMeeting.setStartedAt(pastTime);

        Participation participation = new Participation();
        participation.setParticipantId(memberId);
        List<Participation> list = new ArrayList<>();
        list.add(participation);

        // mocking
        given(dao.get(anyInt())).willReturn(closedMeeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.cancelParticipate(meetingId, memberId));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getReason()).isEqualTo("마감된 모임에 참여를 취소할 수 없습니다");
        assertTrue(closedMeeting.getStartedAt().before(new Date()));
    }

    // 삭제하기 -----------------------------
    @Test
    public void delete_존재하지_않는_모임이라면_NOT_FOUND(){
        //given
        int meetingId = 1;
        Member member = new Member();
        member.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(null);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.delete(meetingId, member));

        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // 내보내기 -----------------------------
    @Test
    public void kick_참여자를_내보낸다() {
        //given
        int meetingId = 1;
        int participantId = 9999;
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(1);

        Participation participation = new Participation();
        participation.setParticipantId(participantId);
        List<Participation> list = new ArrayList<>();
        list.add(participation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);
        given(memberDao.getById(anyInt())).willReturn(new Member());

        // when
        boolean result = service.kick(meetingId, participantId, member);

        // then
        assertThat(result).isEqualTo(true);

        // verify
        verify(participationDao, times(1)).updateBannedAt(anyInt());
    }
    @Test
    public void kick_존재하지_않는_모임이라면_NOT_FOUND() {
        //given
        int meetingId = 1;
        int participantId = 1;
        Member member = new Member();

        // mocking
        given(dao.get(anyInt())).willReturn(null);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.kick(meetingId, participantId, member));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void kick_권한이_없다면_FORBIDDEN() {
        //given
        int meetingId = 1;
        int participantId = 1;
        Member member = new Member();
        member.setId(1);
            
        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(9999);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.kick(meetingId, participantId, member));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void kick_모임에_참여하지_않은_회원이라면_NOTFOUND() {
        //given
        int meetingId = 1;
        int participantId = 1;
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(1);

        Participation otherParticipation = new Participation();
        List<Participation> list = new ArrayList<>();
        list.add(otherParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.kick(meetingId, participantId, member));

        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(e.getReason()).isEqualTo("모임에 참여하지 않는 회원입니다");
    }

    @Test
    public void kick_참여를_취소한_회원이라면_NOTFOUND() {
        //given
        int meetingId = 1;
        int participantId = 1;
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(1);

        Participation canceledParticipation = new Participation();
        canceledParticipation.setCanceledAt(new Date());
        List<Participation> list = new ArrayList<>();
        list.add(canceledParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> service.kick(meetingId, participantId, member));

        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(e.getReason()).isEqualTo("모임에 참여하지 않는 회원입니다");
    }

    @Test
    public void kick_이미_강퇴된_회원이라면_CONFLICT() {
        //given
        int meetingId = 1;
        int participantId = 1;
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(1);

        Participation bannedParticipation = new Participation();
        bannedParticipation.setParticipantId(1);
        bannedParticipation.setBannedAt(new Date());
        List<Participation> list = new ArrayList<>();
        list.add(bannedParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> service.kick(meetingId, participantId, member));

        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(e.getReason()).isEqualTo("이미 강퇴된 회원입니다");
    }

    // TODO: 탈퇴한 회원 테스트

    @Test
    public void kick_자신을_강퇴한다면_BAD_REQUEST() {
        //given
        int meetingId = 1;
        int participantId = 1;
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(1);

        Participation selfParticipation = new Participation();
        selfParticipation.setParticipantId(1);
        List<Participation> list = new ArrayList<>();
        list.add(selfParticipation);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);
        given(participationDao.getListByMeetingId(anyInt())).willReturn(list);
        given(memberDao.getById(anyInt())).willReturn(new Member());

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
                () -> service.kick(meetingId, participantId, member));

        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(e.getReason()).isEqualTo("자기 자신을 내보낼 수 없습니다");
    }

    // 마감하기 -----------------------------
    @Test
    public void close_모임을_마감한다() throws ParseException {
        //given
        Date notStartedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .parse("9999-01-01 11:11:11");
        Meeting testMeeting = new Meeting();
        testMeeting.setStartedAt(notStartedTime);
        testMeeting.setRegMemberId(1);

        Member testMember = new Member();
        testMember.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(testMeeting);

        //when
        boolean result = service.close(testMeeting.getId(), testMember);

        //then
        assertThat(result).isEqualTo(true);

        // verify
        verify(dao, times(1)).updateClosedAt(testMeeting);
    }

    @Test
    public void close_존재하지_않는_모임이라면_NOT_FOUND() {
        //given
        int testMeetingId = 1;
        Member testMember = new Member();
        testMember.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(null);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(testMeetingId, testMember));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void close_권한이_없다면_FORBIDDEN() {
        //given
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setId(1);
        meeting.setRegMemberId(9999);
        
        // mocking
        given(dao.get(anyInt())).willReturn(meeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(meeting.getId(), member));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void close_이미_마감된_모임이라면_CONFLICT() throws ParseException {
        //given
        Member member = new Member();
        member.setId(1);

        Meeting meeting = new Meeting();
        meeting.setClosedAt(new Date());
        meeting.setRegMemberId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(meeting.getId(), member));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void close_이미_시작된_모임이라면_마감처리_후_CONFLICT() throws ParseException {
        //given
        Date pastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .parse("1999-01-01 11:11:11");
        Meeting meeting = new Meeting();
        meeting.setStartedAt(pastTime);
        meeting.setRegMemberId(1);

        Member member = new Member();
        member.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(meeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(meeting.getId(), member));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        // verify
        verify(dao, times(1)).updateClosedAt(meeting);
    }
}