package com.zpop.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.entity.Comment;
import com.zpop.web.entity.CommentView;
@Service
public class DefaultCommentService implements CommentService {
	
	@Autowired
	private CommentDao dao;
	
	@Override
	public List<CommentView> getComment(int meetingId) {
		List<CommentView> list = dao.getComment(meetingId);
		System.out.println(list);
		return list;
	}

	@Override
	public List<CommentView> getReply(int groupId) {
		List<CommentView> list = dao.getReply(groupId);
		System.out.println(list);
		return list;
	}

	@Override
	public int getCountOfComment(int meetingId) {
		int countOfComment= dao.getCountOfComment(meetingId);
		return countOfComment;
	}
	
	@Override
	public int getCountOfReply(int groupId) {
		int countOfReply = dao.getCountOfReply(groupId);
		return countOfReply - 1; //댓글자신의 갯수는 제외.
	}
	


}
