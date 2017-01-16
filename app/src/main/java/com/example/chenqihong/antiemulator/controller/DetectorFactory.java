package com.example.chenqihong.antiemulator.controller;

import android.content.Context;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class DetectorFactory {
    private GeneralDetector mDetector;
    private DetectingListener mListener;
    public interface DetectingListener{
        void onFinished(boolean isEmulator);
    }

    public DetectorFactory(Context context){
        mDetector = new GeneralDetector(context);
        mDetector.setEmulatorCheckedListener(new GeneralDetector.EmulatorCheckedListener() {
            @Override
            public void onChecked(boolean[] status) {
                mListener.onFinished(emulatorDecided(status));

            }
        });
        mDetector.startChecking();
    }

    public boolean emulatorDecided(boolean[] status){
        return false;
    }
}
