package com.example.chenqihong.antiemulator.data;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class BatteryInfo {
    private int level;
    private String sources;
    private String status;
    private int temperature;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
}
