package com.example.chenqihong.antiemulator.controller;

import android.content.Context;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class GeneralDetector {
    private Context mContext;
    private boolean[] mStatus = new boolean[6];
    private boolean[] mFinished = new boolean[6];
    public GeneralDetector(Context context){
        mContext = context;
    }

    private boolean check(boolean status, int index){
        mStatus[index] = status;
        mFinished[index] = status;
        for(int i = 0; i < mFinished.length; i++){
            if(!mFinished[i]){
                return false;
            }
        }

        return true;
    }

}
