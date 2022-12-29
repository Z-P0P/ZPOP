package com.zpop.web.entity;

import java.sql.Date;

public class Member {
    private int id;
    private int socialTypeId;
    private String socialId;
    private String nickname;
    private int fame;
    private String profileImagePath;
    private Date createdAt;
    private Date updatedAt;
    private Date resignedAt;

    private boolean isSuspended;

    public Member() {
    }



    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSocialTypeId() {
        return this.socialTypeId;
    }

    public void setSocialTypeId(int socialTypeId) {
        this.socialTypeId = socialTypeId;
    }

    public String getSocialId() {
        return this.socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFame() {
        return this.fame;
    }

    public void setFame(int fame) {
        this.fame = fame;
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

    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getResignedAt() {
        return this.resignedAt;
    }

    public void setResignedAt(Date resignedAt) {
        this.resignedAt = resignedAt;
    }

    public boolean getIsSuspended() {
        return this.isSuspended;
    }

    public void setIsSuspended(boolean isSuspended) {
  
        this.isSuspended = isSuspended;
    }

}
