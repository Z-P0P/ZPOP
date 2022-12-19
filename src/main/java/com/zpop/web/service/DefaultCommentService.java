package com.zpop.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.entity.Comment;
@Service
public class DefaultCommentService implements CommentService {
	
	@Autowired
	private CommentDao dao;
	
	@Override
	public List<Comment> getComment(int meetingId) {
		List<Comment> list = dao.getComment(meetingId);
		System.out.println(list);
		return list;
	}

	@Override
	public List<Comment> getReply(int parentCommentId) {
		List<Comment> list = dao.getReply(parentCommentId);
		System.out.println(list);
		return list;
	}

}
