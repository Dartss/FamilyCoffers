package com.gorih.familycoffers.model;

public class Category {
    private String name;
    private int color;
    private int icon;
    private final int id;

    public Category(String name, int color, int icon, int id) {
        this.name = name;
        this.color = color;
        this.icon = icon;
        this.id = id;
    }

    public void setName(String name) { this.name = name; }

    public void setColor(int color) { this.color = color; }

    public void setIcon(int icon) { this.icon = icon; }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public int getColor() { return color; }

    public int getIcon() { return icon; }
}
