package com.zpop.web.service;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import static org.junit.jupiter.api.Assertions.fail;
@AutoConfigureTestDatabase(replace=Replace.NONE)
@MybatisTest
class DefaultCommentServiceTest {
	@Autowired
	CommentService service;
	
//	@Test
//	void testGetComment() {
//		service.get
//	}

	@Test
	void testGetReply() {
		fail("Not yet implemented");
	}

}
