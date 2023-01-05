package com.zpop.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.zpop.web.dao.CommentDao;
import com.zpop.web.dao.MeetingDao;
import com.zpop.web.dao.NotificationDao;
import com.zpop.web.entity.Member;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
import com.zpop.web.entity.meeting.Meeting;
import com.zpop.web.utils.ElapsedTimeCalculator;
/*
 * 작성자:임형미, 임우빈(한집안 아님)
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
	@Override
	public List<CommentView> getReplyWithWriter(int memberId, int groupId) {
		List<CommentView> list = dao.getReply(groupId);
		for(CommentView element:list) {
			//본인댓글인지 여부표시
			if(element.getWriterId() == memberId) {
				element.setMyComment(true);
			}
			//작성시간표시
			element.setElapsedTime(ElapsedTimeCalculator.getElpasedTime(element.getCreatedAt()));
			//답글수
			element.setCountOfReply(dao.getCountOfReply(element.getGroupId()));
		} 
		return list;
	}
	@Override
	public Comment getCommentById(int commentId) {
		Comment comment =  dao.getCommentById(commentId);
		return comment;
	}
	@Transactional
	@Override
	public int registerComment(Comment comment) {
		
		int affectedRow = dao.insertComment(comment);

		meetingDao.increaseCommentCount(comment.getMeetingId());

		// 댓글 알림 생성
		int regMemberId = getRegMemberId(comment.getMeetingId());
		createCommentNotification(regMemberId,"/meeting/"+comment.getMeetingId(),3);
		return affectedRow;
	}

	@Transactional
	@Override
	public int registerReply(Comment comment) {
		int affectedRow = dao.insertReply(comment);

		//meetingDao.increaseCommentCount(comment.getMeetingId());

		int commentId = comment.getParentCommentId();
		Comment parentComment = dao.getCommentById(commentId);
		int writerId = parentComment.getWriterId();
		
		createCommentNotification(writerId,"/meeting/"+comment.getMeetingId(),4);
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
	@Override
	public int updateComment(Comment comment, int memberId) {
		// 존재하는 댓글인지 확인
		int commentId = comment.getId();
		Comment foundComment = dao.getCommentById(commentId);
		if(foundComment == null || foundComment.getDeletedAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다");

		// 권한 확인
		if(foundComment.getWriterId() != memberId)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		// 댓글 업데이트. 업데이트가 제대로 되었는지 확인
		String content = comment.getContent();
		int affectedRow = dao.updateComment(commentId, content);
		if(affectedRow == 0)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다");

		return affectedRow;
	}

	@Override
	@Transactional
	public int deleteComment(int commentId, int memberId) {
		// 존재하는 댓글인지 확인
		Comment comment = dao.getCommentById(commentId);
		if(comment == null || comment.getDeletedAt() != null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다");

		// 권한 확인
		if(comment.getWriterId() != memberId)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다");

		// 댓글 삭제. 삭제가 제대로 되었는지 확인
		int affectedRow = dao.deleteComment(commentId);
		if(affectedRow == 0)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다");
		
		// 모임 댓글 수 감소
		if(comment.getGroupId()==0) //답글이 아닌 댓글인 경우만 미팅테이블의 댓글카운트 감소
			meetingDao.decreaseCommentCount(comment.getMeetingId());
		
		return affectedRow;
	}
	
	// 새로운 댓글 생성시 알림 생성을 위한 메서드
	private void createCommentNotification(int memberId, String url, int type) {
		notificationDao.insertCommentNotification(memberId, url, type);
	}
	
	private int getRegMemberId(int meetingId) {
		int regMemberId = meetingDao.getMeetingHost(meetingId);
		return regMemberId;
	}

}
