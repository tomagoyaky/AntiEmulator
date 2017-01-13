package com.example.chenqihong.antiemulator.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.SystemClock;

import com.example.chenqihong.antiemulator.data.SensorBean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.hardware.SensorManager.SENSOR_DELAY_GAME;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class SensorDetector {
    private Context mContext;
    private SensorManager mSensorManager;
    private long mBefore;
    private List<SensorBean> mSensorDataList = new ArrayList<>();
    private interface OnSensorDectectedListener{
        void onFinished(boolean isEmulator);
    }

    public SensorDetector(Context context) {
        mContext = context;
    }

    public void collectAcclerometerData(){
        mSensorManager = (SensorManager)mContext
                .getSystemService(Context.SENSOR_SERVICE);
        mBefore = System.currentTimeMillis();
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mSensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                long after = System.currentTimeMillis();
                if(500 == mBefore - after){
                    checkEmulator();
                }else{
                    SensorBean bean = new SensorBean(x, y, z);
                    mSensorDataList.add(bean);
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        }, sensor, SensorManager.SENSOR_DELAY_GAME);
    }

    private void checkEmulator(){

    }

    private boolean isAllDataSame(){
        Iterator<SensorBean> i = mSensorDataList.iterator();
        SensorBean bean = null;
        if(i.hasNext()) {
            bean = i.next();
        }else{
            return false;
        }

        while(i.hasNext()){
            SensorBean candidate = i.next();
            if(bean.getX() != candidate.getX()
                    || bean.getY() != candidate.getY()
                    || bean.getZ() != candidate.getZ()){
                return false;
            }
        }

        return true;
    }

    private boolean isLikeTag(){
        Iterator<SensorBean> i = mSensorDataList.iterator();
        SensorBean bean = null;
        if(!isLikeTag()){
            return false;
        }

        if(i.hasNext()) {
            bean = i.next();
        }else{
            return false;
        }

        if(bean.getX() == Emulator.SENSOR_TAG_1[0]
                && bean.getY() == Emulator.SENSOR_TAG_1[1]
                && bean.getZ() == Emulator.SENSOR_TAG_1[2]){
            return true;
        }

        if(bean.getX() == Emulator.SENSOR_TAG_2[0]
                && bean.getY() == Emulator.SENSOR_TAG_2[1]
                && bean.getZ() == Emulator.SENSOR_TAG_2[2]){
            return true;
        }


        return true;
    }

}
