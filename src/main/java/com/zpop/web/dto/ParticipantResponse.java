package com.zpop.web.dto;

public class ParticipantResponse {
  private int id;
  private int participantId;
	private String nickname;
	private String profileImagePath;


  public ParticipantResponse() {
  }
  

  public ParticipantResponse(int id,int participantId, String nickname, String profileImagePath) {
    this.id = id;
    this.participantId = participantId;
    this.nickname = nickname;
    this.profileImagePath = profileImagePath;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
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


  public int getParticipantId() {
    return participantId;
  }


  public void setParticipantId(int participantId) {
    this.participantId = participantId;
  }
}
