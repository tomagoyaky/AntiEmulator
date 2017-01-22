package com.example.chenqihong.antiemulator.controller;

/**
 * Created by chenqihong on 2017/1/17.
 */

public class EmulatorPattern {
    public static boolean recognize(boolean[] status){
        if(GeneralDetector.INDEX_ALL != status.length){
            return false;
        }

        if(status[GeneralDetector.INDEX_FILE]
                && status[GeneralDetector.INDEX_NETWORK]
                && status[GeneralDetector.INDEX_SENSOR]
                && ((status[GeneralDetector.INDEX_BATTERY]
                && status[GeneralDetector.INDEX_THERMAL])
                || status[GeneralDetector.INDEX_CPU]
                || status[GeneralDetector.INDEX_PHONEBOOK])){
            return true;
        }

        return false;
    }

}
