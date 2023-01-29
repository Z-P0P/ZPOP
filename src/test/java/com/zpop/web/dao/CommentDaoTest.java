package com.zpop.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
@AutoConfigureTestDatabase(replace=Replace.NONE)
@MybatisTest
class CommentDaoTest {
@Autowired
private CommentDao cmDao;
	@Test
	void test() {
		int meetingId = 2;
		int groupId = 1;
		//댓글
		List<CommentView> list1 = cmDao.getComment(meetingId);
		//답글
		List<CommentView> list2 = cmDao.getReply(groupId);
		//답글 갯수
		int count = cmDao.getCountOfReply(groupId);
		//댓글

		
	}

}
