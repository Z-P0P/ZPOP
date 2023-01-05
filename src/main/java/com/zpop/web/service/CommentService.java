package com.zpop.web.service;

import java.util.List;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
/*
 * 작성자:임형미
 */
public interface CommentService {
	
	List<CommentView> getComment(int meetingId);
	List<CommentView> getCommentWithWriter(int memberId, int meetingId);
	List<CommentView> getReply(int grouopId);
	List<CommentView> getReplyWithWriter(int memberId, int groupId);
	
	Comment getCommentById(int id);
	
	int registerComment(Comment comment);
	int registerReply(Comment comment);
	int updateComment(Comment comment, int memberId);
	int deleteComment(int id, int memberId);
	//reportComment 서비스는 별도의 ReportService 이용
	
	int getCountOfComment(int meetingId);
	int getCountOfReply(int groupId);
	
}
