package com.zpop.web.entity.comment;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentView {
	private int id;
	private int meetingId;
	private int writerId;
	private String nickname;
	private String content;
	private String profileImagePath;
	private int parentCommentId;
	private int groupId;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	private int parentMemberId;
	private String parentNickname;
	private String parentImg;
	private String elapsedTime;
	private int countOfReply; //한 그룹내의 답글 수
	private boolean isMyComment;//본인작성글 여부

	public CommentView(int i) {
		this.groupId = i;
	}
}
