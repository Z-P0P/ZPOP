package com.zpop.web.entity;

import java.util.Date;

public class AgeRange {
    private int id;
    private String type;
    private Date createdAt;
    private Date deletedAt;

    public AgeRange() {
    }

    public AgeRange(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

    public String getType() {
        return type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }
}
