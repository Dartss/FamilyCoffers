package com.gorih.familycoffers.model;

public class Category {
    private final String name;
    private final int color;
    private final int icon;

    public Category(String name, int color, int icon) {
        this.name = name;
        this.color = color;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getColor() { return color; }

    public int getIcon() { return icon; }

}
