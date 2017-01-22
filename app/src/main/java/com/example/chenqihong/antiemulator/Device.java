package com.example.chenqihong.antiemulator;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by chenqihong on 2017/1/11.
 */

public class Device {
    private static String getSystemPropertyInfo(){
        String serialno = getAndroidOsSystemProperties("ro.serialno");
        String hardware = getAndroidOsSystemProperties("ro.hardware");
        return "Serial_NUM " + serialno + "\n" + "HARDWARE_Prop " + hardware + "\n";
    }

    private static String getPhoneInfo(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        String tel = tm.getLine1Number();
        String imei = tm.getSimSerialNumber();
        String imsi = tm.getSubscriberId();
        StringBuilder sb = new StringBuilder();
        sb.append("Telephony_Device_Id ").append(deviceid).append("\n");
        sb.append("Telephone_Number ").append(tel).append("\n");
        sb.append("IMEI ").append(imei).append("\n");
        sb.append("IMSI ").append(imsi).append("\n");
        return sb.toString();
    }


    public static String printDeviceInf(Context context){
        StringBuilder sb = new StringBuilder();
        sb.append(getSystemPropertyInfo());
        sb.append(getPhoneInfo(context));
        sb.append("PRODUCT ").append(android.os.Build.PRODUCT).append("\n");
        sb.append("BOARD ").append(android.os.Build.BOARD).append("\n");
        sb.append("BOOTLOADER ").append(android.os.Build.BOOTLOADER).append("\n");
        sb.append("BRAND ").append(android.os.Build.BRAND).append("\n");
        sb.append("CPU_ABI ").append(android.os.Build.CPU_ABI).append("\n");
        sb.append("CPU_ABI2 ").append(android.os.Build.CPU_ABI2).append("\n");
        sb.append("DEVICE ").append(android.os.Build.DEVICE).append("\n");
        sb.append("DISPLAY ").append(android.os.Build.DISPLAY).append("\n");
        sb.append("FINGERPRINT ").append(android.os.Build.FINGERPRINT).append("\n");
        sb.append("HARDWARE ").append(android.os.Build.HARDWARE).append("\n");
        sb.append("HOST ").append(android.os.Build.HOST).append("\n");
        sb.append("ID ").append(android.os.Build.ID).append("\n");
        sb.append("MANUFACTURER ").append(android.os.Build.MANUFACTURER).append("\n");
        sb.append("MODEL ").append(android.os.Build.MODEL).append("\n");
        sb.append("PRODUCT ").append(android.os.Build.PRODUCT).append("\n");
        sb.append("RADIO ").append(android.os.Build.RADIO).append("\n");
        sb.append("SERIAL ").append(android.os.Build.SERIAL).append("\n");
        sb.append("TAGS ").append(android.os.Build.TAGS).append("\n");
        sb.append("TIME ").append(android.os.Build.TIME).append("\n");
        sb.append("TYPE ").append(android.os.Build.TYPE).append("\n");
        sb.append("USER ").append(android.os.Build.USER).append("\n");
        sb.append("NET_WORK " + getLocalIpAddress()).append("\n");
        //sb.append("FEATURED_FILES " + readFeaturedFiles()).append("\n");
        sb.append("SENSOR_LIST " + getSensorList(context)).append("\n");
        return sb.toString();
    }

    private static String getAndroidOsSystemProperties(String key) {
        String ret;
        try {
            Method systemProperties_get = Class.forName("android.os.SystemProperties").getMethod("get", String.class);
            if ((ret = (String) systemProperties_get.invoke(null, key)) != null)
                return ret;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return "";
    }

    private static String readFeaturedFiles(){

        String dirName = "/";
        File f = new File(dirName);
        File[] files = f.listFiles();
        for(int i = 0; i < files.length; i ++){
            if(files[i].getName().contains("vbox")){
                return "emulator";
            }
        }

        return "None";
    }


    private  static String getLocalIpAddress() {
        String ipAddress = null;
        String hardwareAddress = null;
        StringBuilder sb = new StringBuilder();
        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface iface : interfaces) {
                if(null != iface.getHardwareAddress()) {
                    hardwareAddress = new String(iface.getHardwareAddress(), "UTF-8");
                }
                if (iface.getDisplayName().equals("eth0")) {
                    List<InetAddress> addresses = Collections.list(iface
                            .getInetAddresses());
                    for (InetAddress address : addresses) {
                        if (address instanceof Inet4Address) {
                            ipAddress = address.getHostAddress();
                            sb.append("eth0 ").append(ipAddress).append(" ").append(hardwareAddress).append("\n");
                        }
                    }
                } else if (iface.getDisplayName().equals("wlan0")) {
                    List<InetAddress> addresses = Collections.list(iface
                            .getInetAddresses());
                    for (InetAddress address : addresses) {
                        if (address instanceof Inet4Address) {
                            ipAddress = address.getHostAddress();
                            sb.append("wlan0 ").append(ipAddress).append(" ").append(ipAddress).append("\n");
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static String getSensorList(Context context) {
        // 获取传感器管理器
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        // 获取全部传感器列表
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // 打印每个传感器信息
        StringBuilder strLog = new StringBuilder();
        int iIndex = 1;
        for (Sensor item : sensors) {
            strLog.append(iIndex + ".");
            strLog.append("	Sensor Type - " + item.getType() + "\r\n");
            strLog.append("	Sensor Name - " + item.getName() + "\r\n");
            strLog.append("	Sensor Version - " + item.getVersion() + "\r\n");
            strLog.append("	Sensor Vendor - " + item.getVendor() + "\r\n");
            strLog.append("	Maximum Range - " + item.getMaximumRange() + "\r\n");
            strLog.append("	Minimum Delay - " + item.getMinDelay() + "\r\n");
            strLog.append("	Power - " + item.getPower() + "\r\n");
            strLog.append("	Resolution - " + item.getResolution() + "\r\n");
            strLog.append("\r\n");
            iIndex++;
        }

        return strLog.toString();
        //System.out.println(strLog.toString());
    }

}
