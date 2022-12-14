package com.zpop.web.entity;

public class Category {
    private int id;
    private String name;
    private boolean isActivated;

    public Category() {
    }

    public Category(String name, boolean isActivated) {
        this.name = name;
        this.isActivated = isActivated;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActivated() {
        return isActivated;
    }
}
