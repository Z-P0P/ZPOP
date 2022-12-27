package com.zpop.web.entity;

import java.util.Date;

public class ContactType {
    private int id;
    private String name;
    private Date createdAt;
    private Date deletedAt;

    public ContactType() {
    }

    public ContactType(String name) {
        this.name = name;
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
