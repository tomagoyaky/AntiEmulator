package com.example.chenqihong.antiemulator.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class BatteryDetector {
    private Context mContext;
    private BatteryChangedListener mListener;
    private BroadcastReceiver mBatteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            boolean result = checkTemperature(temperature/10) && checkLevel(level);
            mListener.onBatteryChanged(result);
            mContext.unregisterReceiver(mBatteryReceiver);
        }
    };

    public interface BatteryChangedListener{
        void onBatteryChanged(boolean result);
    }
    public BatteryDetector(Context context, BatteryChangedListener listener){
        mContext = context;
        mListener = listener;
        context.registerReceiver(mBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }


    private boolean checkTemperature(float temperature){
        for(int i = 0; i < Emulator.BATTERY_THERMAL_TAG.length; i++){
            if(temperature == Emulator.BATTERY_THERMAL_TAG[i]){
                return true;
            }
        }

        return false;
    }

    private boolean checkLevel(int level){
        for(int i = 0; i < Emulator.BATTERY_LEVEL_TAG.length; i++){
            if(level == Emulator.BATTERY_LEVEL_TAG[i]){
                return true;
            }
        }

        return false;
    }

}
