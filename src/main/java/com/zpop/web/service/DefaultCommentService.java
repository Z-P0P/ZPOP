package com.zpop.web.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.utils.ElapsedTimeCalculator;
@Service
public class DefaultCommentService implements CommentService {
	
	@Autowired
	private CommentDao dao;
	
	@Override
	public List<CommentView> getComment(int meetingId) {
		List<CommentView> list = dao.getComment(meetingId);
		for(CommentView element:list) {
		    //작성시간표시
		    element.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(element.getCreatedAt()));
		    // 답글 수
		    element.setCountOfReply(dao.getCountOfReply(element.getId()));
		} 
		return list;
	}

	@Override
	public List<CommentView> getReply(int groupId) {
		List<CommentView> list = dao.getReply(groupId);
		for(CommentView element:list) {
		    //작성시간표시
		    element.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(element.getCreatedAt()));
		    //답글수
		    element.setCountOfReply(dao.getCountOfReply(element.getGroupId()));
		} 
		return list;
	}

	
	@Override
	public int registerComment(Comment comment) {
		
		int affectedRow = dao.insertComment(comment);
		return affectedRow;
	}

	@Override
	public int registerReply(Comment comment) {
		int affectedRow = dao.insertReply(comment);
		return affectedRow;
	}
	
	@Override
	public int getCountOfComment(int meetingId) {
		int countOfComment= dao.getCountOfComment(meetingId);
		return countOfComment;
	}
	@Override
	public int getCountOfReply(int groupId) {
		int countOfReply = dao.getCountOfReply(groupId);
		return countOfReply;
	}
	
	
	


}
