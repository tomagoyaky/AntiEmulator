package com.example.chenqihong.antiemulator.controller;

import android.content.Context;
import android.hardware.SensorManager;
import android.provider.ContactsContract;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class GeneralDetector {
    public final static int INDEX_BATTERY = 0;
    public final static int INDEX_CPU = 1;
    public final static int INDEX_FILE = 2;
    public final static int INDEX_NETWORK = 3;
    public final static int INDEX_PHONEBOOK = 4;
    public final static int INDEX_SENSOR = 5;
    public final static int INDEX_STORAGE = 6;
    public final static int INDEX_THERMAL = 7;
    public final static int INDEX_ALL = 8;
    private Context mContext;
    private boolean[] mStatus = new boolean[INDEX_ALL];
    private boolean[] mFinished = new boolean[INDEX_ALL];
    private BatteryDetector batteryDetector;
    private CpuDetector cpuDetector;
    private FileDetector fileDetector;
    private NetworkDetector networkDetector;
    private PhoneBookDetector phoneBookDetector;
    private SensorDetector sensorDetector;
    private StorageDetector storageDetector;
    private ThermalDetector thermalDetector;
    private EmulatorCheckedListener mListener;

    public GeneralDetector(Context context){
        mContext = context;
    }

    public interface EmulatorCheckedListener{
        void onChecked(boolean[] status);
    }

    public void startChecking(){
        checkBattery();
        checkCpu();
        checkNetwork();
        checkPhoneBook();
        checkSeneor();
        checkStorage();
        checkFiles();
        checkThermal();
    }


    public void setEmulatorCheckedListener(EmulatorCheckedListener listener){
        mListener = listener;
    }

    private boolean check(boolean status, int index){
        mStatus[index] = status;
        mFinished[index] = true;
        for(int i = 0; i < mFinished.length; i++){
            if(!mFinished[i]){
                return false;
            }
        }

        mListener.onChecked(mStatus);
        return true;
    }

    private void checkBattery(){
        batteryDetector = new BatteryDetector(mContext, new BatteryDetector.BatteryChangedListener() {
            @Override
            public void onBatteryChanged(boolean result) {
                check(result, INDEX_BATTERY);
            }
        });
    }

    private void checkThermal(){
        thermalDetector = new ThermalDetector();
        thermalDetector.setOnThermalDetectFinishedListener(new ThermalDetector.OnThermalDetectFinishListener() {
            @Override
            public void onFinished(boolean result) {
                check(result, INDEX_THERMAL);
            }
        });
        thermalDetector.detectEmulator();
    }

    private void checkCpu(){
        cpuDetector = new CpuDetector();
        cpuDetector.setOnDetectFinishedListener(new CpuDetector.OnDetectFinishListener() {
            @Override
            public void onFinished(boolean result) {
                check(result, INDEX_CPU);
            }
        });
        cpuDetector.detectEmulator();
    }

    private void checkNetwork(){
        networkDetector = new NetworkDetector();
        check(networkDetector.isEmulator(), INDEX_NETWORK);
    }

    private void checkPhoneBook(){
        phoneBookDetector = new PhoneBookDetector(mContext);
        check(phoneBookDetector.isEmulator(), INDEX_PHONEBOOK);
    }

    private void checkSeneor(){

        sensorDetector = new SensorDetector(mContext);
        sensorDetector.setOnSensorDetectedListener(new SensorDetector.OnSensorDectectedListener() {
            @Override
            public void onFinished(boolean isEmulator) {
                check(isEmulator, INDEX_SENSOR);
            }
        });
        sensorDetector.collectAcclerometerData();
    }

    private void checkStorage(){
        storageDetector = new StorageDetector();
        check(storageDetector.isEmulator(), INDEX_STORAGE);
    }

    private void checkFiles(){
        fileDetector = new FileDetector();
        fileDetector.addDir("/dev");
        fileDetector.addDir("/sys");
        fileDetector.addDir("/system");
        fileDetector.addDir("/system/priv-app");
        check(fileDetector.isEmulator(), INDEX_FILE);
    }

}
