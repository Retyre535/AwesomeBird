package com.example.awesomebird.model;

public abstract class GameObject {
    public float x, y;

    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public abstract void update();
}
