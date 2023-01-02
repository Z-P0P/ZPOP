package com.zpop.web.entity.meeting;

import java.util.Date;


public class Meeting {
    private int id;
    private int regMemberId;
    private int categoryId;
    private int regionId;
    private int ageRangeId;
    private int contactTypeId;
    private int genderCategory;
    private String title;
    private String content;
    private String detailRegion;
    private int maxMember;
    private Date startedAt;
    private String contact;
    private int viewCount;
    private int commentCount;
    private Date closedAt;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    
    public Meeting() {
    }

    public Meeting(int regMemberId, int categoryId, int regionId, int ageRangeId, int contactTypeId, int genderCategory, String title, String content, String detailRegion, int maxMember, Date startedAt, String contact) {
        this.regMemberId = regMemberId;
        this.categoryId = categoryId;
        this.regionId = regionId;
        this.ageRangeId = ageRangeId;
        this.contactTypeId = contactTypeId;
        this.genderCategory = genderCategory;
        this.title = title;
        this.content = content;
        this.detailRegion = detailRegion;
        this.maxMember = maxMember;
        this.startedAt = startedAt;
        this.contact = contact;
    }

	public int getId() {
        return id;
    }

	public void setContactTypeId(int contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void setRegMemberId(int regMemberId) {
		this.regMemberId = regMemberId;
	}


	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}


	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}


	public void setAgeRangeId(int ageRangeId) {
		this.ageRangeId = ageRangeId;
	}


	public void setGenderCategory(int genderCategory) {
		this.genderCategory = genderCategory;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public void setDetailRegion(String detailRegion) {
		this.detailRegion = detailRegion;
	}


	public void setMaxMember(int maxMember) {
		this.maxMember = maxMember;
	}


	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}


	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public void setClosedAt(Date closedAt) {

		this.closedAt = closedAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}


	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}


	public int getRegMemberId() {
        return regMemberId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getRegionId() {
        return regionId;
    }

    public int getAgeRangeId() {
        return ageRangeId;
    }

    public int getContactTypeId() {
        return contactTypeId;
    }

    public int getGenderCategory() {
        return genderCategory;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getDetailRegion() {
        return detailRegion;
    }

    public int getMaxMember() {
        return maxMember;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public String getContact() {
        return contact;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public Date getClosedAt() {
        return closedAt;
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

    @Override

    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", regMemberId=" + regMemberId +
                ", categoryId=" + categoryId +
                ", regionId=" + regionId +
                ", ageRangeId=" + ageRangeId +
                ", contactTypeId=" + contactTypeId +
                ", genderCategory=" + genderCategory +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", detailRegion='" + detailRegion + '\'' +
                ", maxMember=" + maxMember +
                ", startedAt=" + startedAt +
                ", contact='" + contact + '\'' +
                ", viewCount=" + viewCount +
                ", commentCount=" + commentCount +
                ", closedAt=" + closedAt +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
