package com.zpop.web.entity;

public class Region {
    private int id;
    private String name;
    private boolean isActivated;

    public Region() {
    }

    public Region(String name, boolean isActivated) {
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
