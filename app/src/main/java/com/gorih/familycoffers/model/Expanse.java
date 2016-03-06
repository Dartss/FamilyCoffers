package com.gorih.familycoffers.model;

public class Expanse {

    private float value;
    private long date;

    public Expanse(float value, long date) {
        this.value = value;
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public long getDate() {
        return date;
    }
}
