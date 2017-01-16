package com.example.chenqihong.antiemulator.controller;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class PhoneBookDetector {
    private static final String[] PHONE_BOOK = new String[]{
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    };

    private static final String[] CALL_LOG = new String[]{
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.NUMBER,
            CallLog.Calls.DATE,
            CallLog.Calls.DURATION
    };

    private Context mContext;

    public PhoneBookDetector(Context context) {
        mContext = context;
    }

    public boolean isEmulator() {
        return checkCallLog() && checkPhoneBook();
    }

    private boolean checkPhoneBook() {
        ContentResolver resolver = mContext.getContentResolver();

        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                PHONE_BOOK,
                null,
                null,
                null);

        if(null != phoneCursor){
            if(phoneCursor.moveToNext()){
                return false;
            }
        }

        return true;
    }

    private boolean checkCallLog(){
        ContentResolver resolver = mContext.getContentResolver();
        if (ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }

        Cursor phoneCursor = resolver.query(CallLog.Calls.CONTENT_URI, CALL_LOG,
                null,
                null,
                null);

        if(null != phoneCursor){
            if(phoneCursor.moveToNext()){
                return false;
            }
        }

        return true;
    }
}
