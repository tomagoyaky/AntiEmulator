package com.example.chenqihong.antiemulator.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;

import com.example.chenqihong.antiemulator.data.SensorBean;

import java.math.BigDecimal;
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
    private OnSensorDectectedListener mListener;
    private SensorEventListener mEventListener;
    private long mBefore;
    private List<SensorBean> mSensorDataList = new ArrayList<>();
    public interface OnSensorDectectedListener{
        void onFinished(boolean isEmulator);
    }

    public SensorDetector(Context context) {
        mContext = context;
    }

    public void setOnSensorDetectedListener(OnSensorDectectedListener listener){
        mListener = listener;
    }

    public void collectAcclerometerData(){
        mSensorManager = (SensorManager)mContext
                .getSystemService(Context.SENSOR_SERVICE);
        mBefore = System.currentTimeMillis();
        Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                float x = roundHalfUp(event.values[0]);
                float y = roundHalfUp(event.values[1]);
                float z = roundHalfUp(event.values[2]);
                long after = System.currentTimeMillis();
                SensorBean bean = new SensorBean(x, y, z);
                mSensorDataList.add(bean);
                if(2000 < after - mBefore){
                    checkEmulator();
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

        };

        mSensorManager.registerListener(mEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    private float roundHalfUp(float f){
        BigDecimal b = new BigDecimal(f);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    private void checkEmulator(){
        mListener.onFinished(isAllDataSame() | isLikeTag());
        mSensorManager.unregisterListener(mEventListener);
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
        if(!isAllDataSame()){
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
