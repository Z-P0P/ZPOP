package com.zpop.web.service;

import java.util.List;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;

public interface CommentService {
	
	List<CommentView> getComment(int meetingId);
	List<CommentView> getReply(int grouopId);
	
	int registerComment(Comment comment);
	int registerReply(Comment comment);
	
	int getCountOfComment(int meetingId);
	int getCountOfReply(int groupId);

}
