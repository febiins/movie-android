package com.example.movue.model;

public class Rating {
    private float value;

    public Rating(float value) {
        this.value = value;
    }

    public float getValue() { return value; }
    public void setValue(float value) { this.value = value; }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}