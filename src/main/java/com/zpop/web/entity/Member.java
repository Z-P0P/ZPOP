package com.zpop.web.entity;

import java.sql.Timestamp;

public class Member {
    private int id;
    private int socialTypeId;
    private String socialId;
    private String nickname;
    private int fame;
    private String profileImagePath;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp resignedAt;
    private boolean isSuspended;

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

    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getResignedAt() {
        return this.resignedAt;
    }

    public void setResignedAt(Timestamp resignedAt) {
        this.resignedAt = resignedAt;
    }

    public boolean isIsSuspended() {
        return this.isSuspended;
    }

    public void setIsSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

}
