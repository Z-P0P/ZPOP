package com.zpop.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zpop.web.entity.Comment;

public interface CommentService {
	
	List<Comment> getComment(int meetingId);
	List<Comment> getReply(int parentCommentId);

}
