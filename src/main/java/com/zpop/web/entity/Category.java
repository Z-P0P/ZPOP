package com.zpop.web.entity;

import java.util.Date;

public class Category {
    private int id;
    private String name;
    private Date createdAt;
    private Date deletedAt;

    public Category() {
    }


    public Category(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }
}
