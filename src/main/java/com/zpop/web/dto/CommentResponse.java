package com.zpop.web.dto;

import java.util.Date;

public class CommentResponse {
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

  public CommentResponse() {
  }



  public CommentResponse(int id, int meetingId, int writerId, String nickname, String content, String profileImgPath,
		int parentCommentId, int groupId, Date createdAt, Date updatedAt, Date deletedAt, int parentMemberId,
		String parentNickname, String parentImg, String elapsedTime, int countOfReply, boolean isMyComment) {
	this.id = id;
	this.meetingId = meetingId;
	this.writerId = writerId;
	this.nickname = nickname;
	this.content = content;
	this.profileImgPath = profileImgPath;
	this.parentCommentId = parentCommentId;
	this.groupId = groupId;
	this.createdAt = createdAt;
	this.updatedAt = updatedAt;
	this.deletedAt = deletedAt;
	this.parentMemberId = parentMemberId;
	this.parentNickname = parentNickname;
	this.parentImg = parentImg;
	this.elapsedTime = elapsedTime;
	this.countOfReply = countOfReply;
	this.isMyComment = isMyComment;
}



public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getMeetingId() {
    return this.meetingId;
  }

  public void setMeetingId(int meetingId) {
    this.meetingId = meetingId;
  }

  public int getWriterId() {
    return this.writerId;
  }

  public void setWriterId(int writerId) {
    this.writerId = writerId;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getProfileImgPath() {
    return this.profileImgPath;
  }

  public void setProfileImgPath(String profileImgPath) {
    this.profileImgPath = profileImgPath;
  }

  public int getParentCommentId() {
    return this.parentCommentId;
  }

  public void setParentCommentId(int parentCommentId) {
    this.parentCommentId = parentCommentId;
  }

  public int getGroupId() {
    return this.groupId;
  }

  public void setGroupId(int groupId) {
    this.groupId = groupId;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
	return updatedAt;
}

public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
}

public Date getDeletedAt() {
    return this.deletedAt;
  }

  public void setDeletedAt(Date deletedAt) {
    this.deletedAt = deletedAt;
  }

  public int getParentMemberId() {
    return this.parentMemberId;
  }

  public void setParentMemberId(int parentMemberId) {
    this.parentMemberId = parentMemberId;
  }

  public String getParentNickname() {
    return this.parentNickname;
  }

  public void setParentNickname(String parentNickname) {
    this.parentNickname = parentNickname;
  }

  public String getParentImg() {
    return this.parentImg;
  }

  public void setParentImg(String parentImg) {
    this.parentImg = parentImg;
  }

  public String getElapsedTime() {
    return this.elapsedTime;
  }

  public void setElapsedTime(String elapsedTime) {
    this.elapsedTime = elapsedTime;
  }

  public int getCountOfReply() {
    return this.countOfReply;
  }

  public void setCountOfReply(int countOfReply) {
    this.countOfReply = countOfReply;
  }

  public boolean isMyComment() {
    return this.isMyComment;
  }

  public void setMyComment(boolean isMyComment) {
    this.isMyComment = isMyComment;
  }

}
