package com.zpop.web.entity.comment;

import java.util.Date;

public class CommentView {
	private int id;
	private int meetingId;
	private int writerId;
	private String nickname;
	private String content;
	private String profileImgPath;
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
	
	public CommentView() {
	}

	public CommentView(int i) {
		this.groupId = i;
	}

	public int getId() {
		return id;
	}

	public int getMeetingId() {
		return meetingId;
	}

	public int getWriterId() {
		return writerId;
	}

	public String getNickname() {
		return nickname;
	}

	public String getContent() {
		return content;
	}

	public String getProfileImgPath() {
		return profileImgPath;
	}

	public int getParentCommentId() {
		return parentCommentId;
	}

	public int getGroupId() {
		return groupId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public int getParentMemberId() {
		return parentMemberId;
	}

	public String getParentNickname() {
		return parentNickname;
	}

	public String getParentImg() {
		return parentImg;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public int getCountOfReply() {
		return countOfReply;
	}

	public void setCountOfReply(int countOfReply) {
		this.countOfReply = countOfReply;
	}

	public boolean isMyComment() {
		return isMyComment;
	}

	public void setMyComment(boolean isMyComment) {
		this.isMyComment = isMyComment;
	}

	@Override
	public String toString() {
		return "CommentView [id=" + id + ", meetingId=" + meetingId + ", nickname=" + nickname + ", content=" + content
				+ ", profileImgPath=" + profileImgPath + ", parentCommentId=" + parentCommentId + ", groupId=" + groupId
				+ ", createdAt=" + createdAt + ", parentMemberId=" + parentMemberId + ", parentNickname="
				+ parentNickname + ", parentImg=" + parentImg + "]";
	}

	

	

	
	
	
}
