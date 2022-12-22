package com.zpop.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
@Mapper
public interface CommentDao {
		
		/* groupId : 1depth아이디 */
		
		List<CommentView> getComment(int meetingId);
		List<CommentView> getReply(int groupId);
		int getCountOfComment(int meetingId);
		int getCountOfReply(int groupId);
		
		int insertComment(Comment comment); //댓글
		int insertReply(Comment comment); //대댓글

		/*
		 * 대댓글에 대한 댓글의 경우 직계의 parentId를 통해 depth1의 조부모Id값을 얻을 수 있으므로 오버로딩 생략.
		 */
		int updateComment(Comment cmt);
		int deleteComment(int id);


}
