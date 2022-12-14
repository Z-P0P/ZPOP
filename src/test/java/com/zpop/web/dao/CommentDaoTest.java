package com.zpop.web.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
@AutoConfigureTestDatabase(replace=Replace.NONE)
@MybatisTest
class CommentDaoTest {
@Autowired
private CommentDao cmDao;
	@Test
	void test() {
		//List<Comment> list = cmDao.getList(1);
		//System.out.println(list);
		
//		Map<String,String> map = new HashMap<>();
//		map.put("meetingId", "1");
//		map.put("writerId", "1");
//		map.put("parentCommentId", "1");
//		map.put("content", "상품은 뭔가요?");
//		int affectedRow = cmDao.createComment(map);
//		System.out.println(affectedRow);
		
		Map<String,String> map = new HashMap<>();
		map.put("meetingId", "1");
		map.put("parentId", "1");
		map.put("writerId", "1");
		map.put("content", "뭔 x소리냐 ㅋㅋ");
		int affectedRow = cmDao.createReply(map);
		System.out.println(affectedRow);
		
//		Comment cmt = new Comment();
//		cmt.setId(1);
//		cmt.setContent("상품을 안준다고요???");
//		int affectedRow = cmDao.updateComment(cmt);
//		System.out.println(affectedRow);
		
//		int affectedRow = cmDao.deleteComment(1);
	}

}
