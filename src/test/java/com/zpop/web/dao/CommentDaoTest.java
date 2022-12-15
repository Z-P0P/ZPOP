package com.zpop.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.zpop.web.entity.Comment;
@AutoConfigureTestDatabase(replace=Replace.NONE)
@MybatisTest
class CommentDaoTest {
@Autowired
private CommentDao cmDao;
	@Test
	void test() {
		int meetingId = 1;
		int groupId = 1;
		List<Comment> list1 = cmDao.getComment(meetingId);
		System.out.println(list1);
		List<Comment> list2 = cmDao.getReply(groupId);
		System.out.println(list2);
		//댓글
		Map<String,String> map = new HashMap<>();
		map.put("meetingId", "1");
		map.put("writerId", "1");
		map.put("content", "상품은 뭔가요?");
		int affectedRow = cmDao.insertComment(map);
		System.out.println(affectedRow);
		//대댓글
		Map<String,String> map2 = new HashMap<>();
		map2.put("meetingId", "1");
		map2.put("parentCommentId", "1");
		map2.put("writerId", "2");
		map2.put("content", "뭔 소리냐 ㅋㅋ");
		int affectedRow2 = cmDao.insertReply(map2);
		System.out.println(affectedRow2);
		//대댓글에 대한 댓글
		Map<String,String> map3 = new HashMap<>();
		map3.put("meetingId", "1");
		map3.put("parentCommentId", "1");
		map3.put("groupId", "1");
		map3.put("writerId", "1");
		map3.put("content", "존댓말 씁시다");
		int affectedRow3 = cmDao.insertReply(map3);
		System.out.println(affectedRow3);
		
		//수정
		Comment cmt = new Comment();
		cmt.setId(1);
		cmt.setContent("상품을 안준다고요???");
		int affectedRow4 = cmDao.updateComment(cmt);
		System.out.println(affectedRow4);
		
	}

}
