package com.zpop.web.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
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
