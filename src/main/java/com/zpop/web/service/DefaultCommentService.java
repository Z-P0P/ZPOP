package com.zpop.web.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.NotificationDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.utils.ElapsedTimeCalculator;
/*
 * 작성자:임형미
 */
@Service
public class DefaultCommentService implements CommentService {
	
	@Autowired
	private CommentDao dao;
	
	@Autowired
	private MeetingDao meetingDao;
	
	@Autowired
	private NotificationDao notificationDao;
	
	
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
	public List<CommentView> getCommentWithWriter(int memberId, int meetingId) {
		List<CommentView> list = dao.getComment(meetingId);
		for(CommentView element:list) {
			//본인댓글인지 여부표시
			if(element.getWriterId() == memberId)
				element.setMyComment(true);
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

	@Transactional
	@Override
	public int registerComment(Comment comment) {
		
		int affectedRow = dao.insertComment(comment);
		// 댓글 알림 생성
		int regMemberId = getRegMemberId(comment.getMeetingId());
		createCommentNotification(regMemberId,"/meeting/"+comment.getMeetingId(),3);
		return affectedRow;
	}

	@Transactional
	@Override
	public int registerReply(Comment comment) {
		int affectedRow = dao.insertReply(comment);
		int regMemberId = getRegMemberId(comment.getMeetingId());
		createCommentNotification(regMemberId,"/meeting/"+comment.getMeetingId(),4);
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

	// 새로운 댓글 생성시 알림 생성을 위한 메서드
	@Override
	public void createCommentNotification(int memberId, String url, int type) {
		notificationDao.insertCommentNotification(memberId, url, type);
	}
	
	private int getRegMemberId(int meetingId) {
		int regMemberId = meetingDao.getMeetingHost(meetingId);
		return regMemberId;
	}
}
