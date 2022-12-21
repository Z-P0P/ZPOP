package com.zpop.web.dao;

import com.zpop.web.entity.meeting.MeetingThumbView;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
class MeetingDaoTest {
    @Autowired
    private MeetingDao meetingDao;
    @Test
    void getMeetingList() {
        List<MeetingThumbView> result = meetingDao.getMeetingList(7);
        System.out.println(result);
    }
}