package com.zpop.web.entity;

import java.util.Date;

public class CommentView {
	private int id;
	private int meetingId;
	private String nickname;
	private String content;
	private String profileImgPath;
	private int parentCommentId;
	private int groupId;
	private Date createdAt;
	private int parentMemberId;
	private String parentNickname;
	private String parentImg;
	private String elapsedTime;
	private int countOfReply; //한 그룹내의 답글 수
	
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

	@Override
	public String toString() {
		return "CommentView [id=" + id + ", meetingId=" + meetingId + ", nickname=" + nickname + ", content=" + content
				+ ", profileImgPath=" + profileImgPath + ", parentCommentId=" + parentCommentId + ", groupId=" + groupId
				+ ", createdAt=" + createdAt + ", parentMemberId=" + parentMemberId + ", parentNickname="
				+ parentNickname + ", parentImg=" + parentImg + "]";
	}

	

	

	
	
	
}
