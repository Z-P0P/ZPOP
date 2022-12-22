
package com.zpop.web.dao;

import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.Date;
import java.util.List;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MeetingDaoTest {

        @Autowired
        private MeetingDao meetingDao;

        @Test
        void insert_테스트() {
                Meeting testMeeting = new Meeting(
                                1,
                                1,
                                1,
                                1,
                                0,
                                "모임 테스트 제목",
                                "모임 테스트 내용",
                                "뉴렉처 강의실",
                                5,
                                new Date(),
                                "뉴렉처 카카오톡");
                int result = meetingDao.insert(testMeeting);
                assertThat(result).isEqualTo(1);
        }

        @Test
        void get_테스트() {
                Meeting meeting = meetingDao.get(1);
                System.out.println(meeting.getTitle());
        }

        @Test
        void get_list_테스트() {
                MeetingThumbnailPagination pagination = new MeetingThumbnailPagination(0, null, null);

                List<MeetingThumbnailView> meetings = meetingDao.getThumbnailViewList(pagination);
                for (MeetingThumbnailView m : meetings)
                        System.out.println(m.getTitle());
        }
}