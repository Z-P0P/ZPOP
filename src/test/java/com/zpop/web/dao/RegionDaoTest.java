package com.zpop.web.dao;

import com.zpop.web.entity.Region;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegionDaoTest {

    @Autowired
    private RegionDao regionDao;

    @Test
    void get_테스트() {
        Region region = regionDao.get(1);
        System.out.println(region.getName());
    }

    @Test
    void get_list_테스트() {
        List<Region> regions = regionDao.getList();
        for (Region r : regions)
            System.out.println(r.getName());
    }
}