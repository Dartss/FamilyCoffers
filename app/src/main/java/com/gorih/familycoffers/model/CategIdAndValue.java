package com.gorih.familycoffers.model;

public class CategIdAndValue {
    private int categoyrId;
    private float valueSum;

    public CategIdAndValue(int categoyrId, float valueSum) {
        this.categoyrId = categoyrId;
        this.valueSum = valueSum;
    }

    public int getCategoyrId() {
        return categoyrId;
    }

    public float getValueSum() {
        return valueSum;
    }
}
