package com.example.chenqihong.antiemulator.controller;

import com.example.chenqihong.antiemulator.data.SensorBean;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class Emulator {
    public static String[] QEMU_TAG = {"qemu"};
    public static String[] GENYMOTION_TAG = {"vbox", "geny"};
    public static String[] BLUESTACK_TAG = {"bluestacks"};
    public static String[] HAIMAWAN_TAG = {"vbox"};
    public static int[] CPU_FREQ_TAG= {0, 2000};
    public static double[] SENSOR_TAG_1 = {0, 0, 9.8};
    public static double[] SENSOR_TAG_2 = {0, 9.8, 0.8};
}
