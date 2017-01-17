package com.example.chenqihong.antiemulator.controller;

/**
 * Created by chenqihong on 2017/1/17.
 */

public class EmulatorPattern {
    public static boolean recognize(boolean[] status){
        if(7 != status.length){
            return false;
        }

        if(status[2] && status[3] && status[5] && (status[0] || status[1] || status[4])){
            return true;
        }

        return false;
    }

}
