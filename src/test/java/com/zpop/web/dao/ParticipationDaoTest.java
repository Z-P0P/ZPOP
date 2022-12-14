package com.zpop.web.dao;

import com.zpop.web.entity.Participation;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParticipationDaoTest {

    @Autowired
    private ParticipationDao participationDao;

    @Test
    void insert_테스트() {
        Participation testParticipation = new Participation(
                1,
                1
        );
        int result = participationDao.insert(testParticipation);
        assertThat(result).isEqualTo(1);
    }
}