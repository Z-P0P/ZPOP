package com.zpop.web.service;

import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.ParticipationDao;
import com.zpop.web.entity.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

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

}