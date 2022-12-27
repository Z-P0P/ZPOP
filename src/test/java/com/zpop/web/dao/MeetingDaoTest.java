
package com.zpop.web.dao;

import com.zpop.web.dto.MeetingThumbnailPagination;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.entity.meeting.MeetingThumbnailView;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MeetingDaoTest {

        @Autowired
        private MeetingDao meetingDao;

        @Test
        void get_테스트() {
                Meeting meeting = meetingDao.get(1);
                System.out.println(meeting.getTitle());
        }

        @Test
        void get_list_전체_조회_테스트() {
                int startId = 0;
                String keyword = null;
                Integer categoryId = null;
                String[] regionIds = null;
                Boolean isClosed = null;
                MeetingThumbnailPagination pagination =
                        new MeetingThumbnailPagination(startId, keyword, categoryId, regionIds, isClosed);

                List<MeetingThumbnailView> meetings = meetingDao.getThumbnailViewList(pagination);
                for (MeetingThumbnailView m : meetings)
                        System.out.println(m.getTitle());
        }
}