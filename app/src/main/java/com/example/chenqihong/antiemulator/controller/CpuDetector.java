package com.example.chenqihong.antiemulator.controller;

import android.os.CountDownTimer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class CpuDetector {
    private static long LENGTH = 2000;
    private static long STEP = 10;
    private boolean mResult;
    private OnDetectFinishListener mListener;
    public interface OnDetectFinishListener{
        void onFinished(boolean result);
    }
    public void detectEmulator(){
        final List<String> freq = new ArrayList<>();
        CountDownTimer timer = new CountDownTimer(LENGTH, STEP) {
            @Override
            public void onTick(long millisUntilFinished) {
                freq.add(getCurrentCpuFreq());
            }

            @Override
            public void onFinish() {
                mResult = isSameFreq(freq) && isMatchedTag(freq);
                mListener.onFinished(mResult);
            }
        };

        timer.start();
    }

    public void setOnDetectFinishedListener(OnDetectFinishListener listener){
        mListener = listener;
    }

    private String getCurrentCpuFreq(){
        String result = null;
        try {
            FileReader fr = new FileReader(
                    "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq");
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

    private boolean isSameFreq(List<String> freq){
        if(freq.size() < 5){
            return false;
        }

        Iterator<String> i = freq.iterator();
        String target = i.next();
        if(null == target){
            return true;
        }

        while (i.hasNext()){
            if(!target.equals(i.next())){
                return false;
            }
        }

        return true;
    }

    private boolean isMatchedTag(List<String> freq){
        if(!isSameFreq(freq)){
            return false;
        }

        String tag = freq.get(0);
        if(null == tag){
            return true;
        }

        for(int i = 0; i < Emulator.CPU_FREQ_TAG.length; i++){
            if(Emulator.CPU_FREQ_TAG[i].equals(tag)){
                return true;
            }
        }

        return false;
    }

}
