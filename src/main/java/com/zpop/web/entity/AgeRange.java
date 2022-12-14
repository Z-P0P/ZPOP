package com.zpop.web.entity;

public class AgeRange {
    private int id;
    private String type;

    public AgeRange() {
    }

    public AgeRange(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
