package com.zpop.web.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.zpop.web.dto.admin.AdminCommentDto;
import com.zpop.web.entity.comment.Comment;
import com.zpop.web.entity.comment.CommentView;
/*
 * 작성자: 임형미
 */
@Mapper
public interface CommentDao {

	/* groupId : 1depth아이디 */

	List<CommentView> getComment(int meetingId);
	List<CommentView> getReply(int groupId);
	Comment getCommentById(int commentId);

//	getCountOfComment 는 meetingDao로 이관.
	int getCountOfReply(int groupId);

	int insertComment(Comment comment); // 댓글
	int insertReply(Comment comment); // 대댓글

	/*
	 * 대댓글에 대한 댓글의 경우 직계의 parentId를 통해 depth1의 조부모Id값을 얻을 수 있으므로 오버로딩 생략.
	 */
	int updateComment(int commentId, String content);
	int deleteComment(int commentId); //레코드에 삭제날짜를 설정. 실제 삭제 x

	List<AdminCommentDto> getAdminList(int offset, int size, String keyword, String option, Date minDate, Integer period, String order);
	
	int countBySearch(String keyword, String option, Date minDate, Integer period);

	List<Comment> getByIds(List<Integer> ids);

	int removeAllDeletedAt(List<Integer> ids);

	int updateAllDeletedAt(List<Integer> ids);
}
