package com.gorih.familycoffers.model;

public class Expanse {

    private float value;
    private long date;
    private String categoryName;

    public Expanse(float value, long date, String categoryName) {
        this.value = value;
        this.date = date;
        this.categoryName = categoryName;
    }

    public float getValue() {
        return value;
    }

    public long getDate() {
        return date;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
