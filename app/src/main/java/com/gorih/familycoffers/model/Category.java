package com.gorih.familycoffers.model;

import java.util.ArrayList;

public class Category {
    private String name;
    private int color;
    private ArrayList<Expanse> expanses;

    public Category(String name, int color) {
        this.name = name;
        this.color = color;
        expanses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }

    public void addExpanse(Expanse newExpanse) {
        expanses.add(newExpanse);
    }
}
