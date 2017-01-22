package com.example.chenqihong.antiemulator.controller;

import android.os.CountDownTimer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenqihong on 2017/1/22.
 */

public class ThermalDetector {
    private static long LENGTH = 2000;
    private static long STEP = 10;
    private boolean mResult;
    private OnThermalDetectFinishListener mListener;
    public interface OnThermalDetectFinishListener{
        void onFinished(boolean result);
    }

    public void detectEmulator(){
        final List<List<Float>> thermals = new ArrayList<>();
        CountDownTimer timer = new CountDownTimer(LENGTH, STEP) {
            @Override
            public void onTick(long millisUntilFinished) {
                getThermal(thermals);
            }

            @Override
            public void onFinish() {
                mResult = isSameThermal(thermals);
                mListener.onFinished(mResult);
            }
        };

        timer.start();
    }

    public void setOnThermalDetectFinishedListener(OnThermalDetectFinishListener listener){
        mListener = listener;
    }

    private boolean isSameThermal(List<List<Float>> thermalList){
        Iterator<List<Float>> iThermalList = thermalList.listIterator();
        while(iThermalList.hasNext()){
            Iterator<Float> iThermal = iThermalList.next().iterator();
            float before = iThermal.next();
            while(iThermal.hasNext()){
                float current = iThermal.next();
                if(before != current){
                    return false;
                }
            }
        }

        return true;
    }

    private void getThermal(List<List<Float>> thermalList){
        String rootPath = "/sys/class/thermal/";
        File file = new File(rootPath);
        File[] partialThermalList = file.listFiles();
        if(null == partialThermalList){
            return;
        }

        List<Float> thermal = new ArrayList<>();
        for(int i = 0; i < partialThermalList.length; i++){
            if(partialThermalList[i].getName().contains("thermal_zone")){
                String thermalStr = getCurrentThermals(partialThermalList[i].getAbsolutePath());
                if(null != thermalStr) {
                    thermal.add(Float.parseFloat(thermalStr) / 100);
                }
            }
        }

        if(0 < thermal.size()) {
            thermalList.add(thermal);
        }

        return;
    }

    private String getCurrentThermals(String path){
        String result = null;
        try {
            FileReader fr = new FileReader(path + "/temp");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            result = text.trim();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
