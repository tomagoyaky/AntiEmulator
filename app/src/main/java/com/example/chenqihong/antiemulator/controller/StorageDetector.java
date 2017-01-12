package com.example.chenqihong.antiemulator.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class StorageDetector {
    public boolean isEmulator(){
        return findStorage();
    }

    private boolean findStorage(){
        File file = new File("/dev");
        String[] fileName = file.list();
        if(null == fileName){
            return false;
        }

        for(int i = 0; i < fileName.length; i++){
            if(fileName[i].contains("mmcblk0")){
                return true;
            }
        }

        return false;
    }
}
