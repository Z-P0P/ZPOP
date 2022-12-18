package com.zpop.web.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zpop.web.entity.CommentView;
@SpringBootTest
class CommentServiceTest {
	@Autowired
	CommentService service;
	@Test
	void testGetComment() {
		System.out.println(service.getComment(1));
	}

	@Test
	void testGetReply() {
		CommentView cmt = new CommentView(1);
		
		System.out.println(service.getReply(4));
	}
	
}
