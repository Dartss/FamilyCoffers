package com.gorih.familycoffers.model;

public class Expanse {

    private long id;
    private float value;
    private long date;
    private Category category;

    public Expanse(float value, long date, Category categoryName) {
        this.value = value;
        this.date = date;
        this.category = categoryName;
    }

    public Expanse() { }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getId() { return id; }

    public float getValue() { return value; }

    public long getDate() {
        return date;
    }

    public String getCategoryName() {
        return category.getName();
    }

    public Category getCategory() { return category; }

    @Override
    public String toString() {
        return this.category.getName() + " " + this.value + " " + this.date;
    }
}
