package com.example.chenqihong.antiemulator.data;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class SensorInfo {
    private int sensorNumber;
    private boolean isGyroSensorFixed;

    public int getSensorNumber() {
        return sensorNumber;
    }

    public void setSensorNumber(int sensorNumber) {
        this.sensorNumber = sensorNumber;
    }

    public boolean isGyroSensorFixed() {
        return isGyroSensorFixed;
    }

    public void setGyroSensorFixed(boolean gyroSensorFixed) {
        isGyroSensorFixed = gyroSensorFixed;
    }
}
