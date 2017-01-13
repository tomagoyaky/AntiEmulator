package com.example.chenqihong.antiemulator.data;

/**
 * Created by chenqihong on 2017/1/13.
 */

public class SensorBean {
    private float x;
    private float y;
    private float z;

    public SensorBean(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
