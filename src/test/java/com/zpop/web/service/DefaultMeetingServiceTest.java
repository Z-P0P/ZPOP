package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.meeting.Meeting;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
class DefaultMeetingServiceTest {

    @InjectMocks
    private MeetingService service = new DefaultMeetingService();

    @Mock
    private MeetingDao dao;

    @Mock
    private ParticipationDao participationDao;

    @Test
    public void 에러_존재하지_않는_모임(){
        //given
        int testMeetingId = 1;
        Member testMember = new Member();
        testMember.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(null);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.delete(testMeetingId, testMember));

        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // 마감하기 -----------------------------
    @Test
    public void 모임을_마감한다() throws ParseException {
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
    public void 존재하지_않는_모임이라면_NOT_FOUND() {
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
    public void 권한이_없다면_FORBIDDEN() {
        //given
        Meeting testMeeting = new Meeting();
        testMeeting.setId(1);
        testMeeting.setRegMemberId(1);

        Member testMember = new Member();
        testMember.setId(9999);

        // mocking
        given(dao.get(anyInt())).willReturn(testMeeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(testMeeting.getId(), testMember));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 이미_마감된_모임이라면_CONFLICT() throws ParseException {
        //given
        Meeting testMeeting = new Meeting();
        testMeeting.setClosedAt(new Date());
        testMeeting.setRegMemberId(1);

        Member testMember = new Member();
        testMember.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(testMeeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(testMeeting.getId(), testMember));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    public void 이미_시작된_모임이라면_마감처리_후_CONFLICT() throws ParseException {
        //given
        Date pastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .parse("1999-01-01 11:11:11");
        Meeting testMeeting = new Meeting();
        testMeeting.setStartedAt(pastTime);
        testMeeting.setRegMemberId(1);

        Member testMember = new Member();
        testMember.setId(1);

        // mocking
        given(dao.get(anyInt())).willReturn(testMeeting);

        //when
        ResponseStatusException e = assertThrows(ResponseStatusException.class,
            () -> service.close(testMeeting.getId(), testMember));
        
        //then
        assertThat(e.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        // verify
        verify(dao, times(1)).updateClosedAt(testMeeting);
    }
}