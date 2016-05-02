package com.gorih.familycoffers.model;

public class Expanse {

    private long id;
    private float value;
    private long date;
    private String category;

    public Expanse(float value, long date, String categoryName) {
        this.value = value;
        this.date = date;
        this.category = categoryName;
    }

    public Expanse() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {

        return id;
    }

    public float getValue() { return value; }

    public long getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }


    @Override
    public String toString() {
        return this.category + " " + this.value + " " + this.date;
    }
}
