package com.example.chenqihong.antiemulator.data;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class NetworkInfo {
    private boolean isEth0;
    private boolean hasNoWlan0;

    public boolean isEth0() {
        return isEth0;
    }

    public void setEth0(boolean eth0) {
        isEth0 = eth0;
    }

    public boolean isHasNoWlan0() {
        return hasNoWlan0;
    }

    public void setHasNoWlan0(boolean hasNoWlan0) {
        this.hasNoWlan0 = hasNoWlan0;
    }
}
