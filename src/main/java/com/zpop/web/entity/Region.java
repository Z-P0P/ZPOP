package com.zpop.web.entity;

import java.util.Date;

public class Region {
    private int id;
    private String name;
    private Date createdAt;
    private Date deletedAt;

    public Region() {
    }

    public Region(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
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
