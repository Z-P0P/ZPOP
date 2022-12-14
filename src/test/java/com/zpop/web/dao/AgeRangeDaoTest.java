package com.zpop.web.dao;

import com.zpop.web.entity.AgeRange;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AgeRangeDaoTest {

    @Autowired
    private AgeRangeDao ageRangeDao;

    @Test
    void get_테스트() {
        AgeRange ageRange = ageRangeDao.get(1);
        System.out.println(ageRange.getType());
    }

    @Test
    void get_list_테스트() {
        List<AgeRange> ageRanges = ageRangeDao.getList();
        for (AgeRange a : ageRanges)
            System.out.println(a.getType());
    }
}