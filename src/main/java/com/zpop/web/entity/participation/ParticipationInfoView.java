package com.zpop.web.entity.participation;

import java.util.Date;

public class ParticipationInfoView {
  private int id;
  private int participantId;
  private String nickname;
  private String profileImagePath;
  private Date createdAt;
  private Date bannedAt;
  private Date canceledAt;
  private boolean hasEvaluated;

  public ParticipationInfoView() {
  }



  public ParticipationInfoView(int id, int participantId, String nickname, String profileImagePath, Date createdAt,
		Date bannedAt, Date canceledAt, boolean hasEvaluated) {
	super();
	this.id = id;
	this.participantId = participantId;
	this.nickname = nickname;
	this.profileImagePath = profileImagePath;
	this.createdAt = createdAt;
	this.bannedAt = bannedAt;
	this.canceledAt = canceledAt;
	this.hasEvaluated = hasEvaluated;
}



public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getParticipantId() {
    return this.participantId;
  }

  public void setParticipantId(int participantId) {
    this.participantId = participantId;
  }

  public String getNickname() {
    return this.nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getProfileImagePath() {
    return this.profileImagePath;
  }

  public void setProfileImagePath(String profileImagePath) {
    this.profileImagePath = profileImagePath;
  }

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getBannedAt() {
    return this.bannedAt;
  }

  public void setBannedAt(Date bannedAt) {
    this.bannedAt = bannedAt;
  }

  public Date getCanceledAt() {
    return this.canceledAt;
  }

  public void setCanceledAt(Date canceledAt) {
    this.canceledAt = canceledAt;
  }

public boolean hasEvaluated() {
	return hasEvaluated;
}

public void setEvaluated(boolean hasEvaluated) {
	this.hasEvaluated = hasEvaluated;
}

 

}
