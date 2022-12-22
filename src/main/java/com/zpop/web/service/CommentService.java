package com.zpop.web.service;

import java.util.List;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;

public interface CommentService {
	
	List<CommentView> getComment(int meetingId);
	List<CommentView> getReply(int grouopId);
	int getCountOfComment(int meetingId);
	
	int registerComment(Comment comment);

}
