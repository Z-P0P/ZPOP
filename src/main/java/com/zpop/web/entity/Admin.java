package com.zpop.web.entity;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class Admin {
    private int id;
    private String name;
    private String pwd;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
}
