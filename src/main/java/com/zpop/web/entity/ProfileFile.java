package com.zpop.web.entity;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
public class ProfileFile {

    private int id;
    private int memberId;
    private String name;
    private Date createdAt;
    private Date deletedAt;

    public ProfileFile(String name) {
        this(name, 0);
    }

    public ProfileFile(String name, int memberId) {
        this.name = name;
        this.memberId = memberId;
    }
}
