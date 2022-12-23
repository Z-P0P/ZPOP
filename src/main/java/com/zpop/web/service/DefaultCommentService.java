package com.zpop.web.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.entity.Comment;
import com.zpop.web.entity.CommentView;
import com.zpop.web.utils.ElapsedTimeCalculator;
@Service
public class DefaultCommentService implements CommentService {
	
	@Autowired
	private CommentDao dao;
	
	@Override
	public List<CommentView> getComment(int meetingId) {
		List<CommentView> list = dao.getComment(meetingId);

		Iterator<CommentView> iterator = list.iterator();
		while (iterator.hasNext()) {
		    CommentView element = iterator.next();
		    //작성시간표시
		    element.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(element.getCreatedAt()));
		    // 답글 수에서 댓글 자신의 수는 제외
		    element.setCountOfReply(dao.getCountOfReply(element.getGroupId()) - 1);
		    
		} 
		return list;
	}

	@Override
	public List<CommentView> getReply(int groupId) {
		List<CommentView> list = dao.getReply(groupId);
		
		Iterator<CommentView> iterator = list.iterator();
		while (iterator.hasNext()) {
		    CommentView element = iterator.next();
		    //작성시간표시
		    element.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(element.getCreatedAt()));
		    // 답글 수에서 댓글 자신의 수는 제외
		    element.setCountOfReply(dao.getCountOfReply(element.getGroupId()) - 1);
		} 
				
		return list;
	}

	@Override
	public int getCountOfComment(int meetingId) {
		int countOfComment= dao.getCountOfComment(meetingId);
		return countOfComment;
	}

	@Override
	public int registerComment(int meetingId, Comment comment) {
		
		int affectedRow = dao.insertComment(comment);
		return affectedRow;
	}
	
	
	


}
