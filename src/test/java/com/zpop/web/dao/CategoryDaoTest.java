package com.zpop.web.dao;

import com.zpop.web.entity.Category;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryDaoTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    void get_테스트() {
        Category category = categoryDao.get(1);
        System.out.println(category.getName());
    }

    @Test
    void get_list_테스트() {
        List<Category> categories = categoryDao.getList();
        for(Category c : categories)
            System.out.println(c.getName());
    }
}