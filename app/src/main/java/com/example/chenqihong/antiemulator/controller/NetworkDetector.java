package com.example.chenqihong.antiemulator.controller;

import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class NetworkDetector {
    public boolean isEmulator(){
        return checkLocalIpAddress();
    }
    private boolean checkLocalIpAddress() {
        boolean eth0hasIp = false;
        boolean eth0hasMac = false;
        boolean wlan0hasIp = false;
        boolean wlan0hasMac = false;
        boolean isEth0 = false;
        boolean isWlan0 = false;

        try {
            List<NetworkInterface> interfaces = Collections
                    .list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface iface : interfaces) {

                if (iface.getDisplayName().equals("eth0")) {
                    isEth0 = true;
                    List<InetAddress> addresses = Collections.list(iface
                            .getInetAddresses());
                    byte[] mac = iface.getHardwareAddress();
                    if(null != addresses){
                        eth0hasIp = true;
                    }

                    if(null != mac){
                        eth0hasMac = true;
                    }
                } else if (iface.getDisplayName().equals("wlan0")) {
                    isWlan0 = true;
                    List<InetAddress> addresses = Collections.list(iface
                            .getInetAddresses());
                    byte[] mac = iface.getHardwareAddress();
                    if(null != addresses){
                        wlan0hasIp = true;
                    }

                    if(null != mac){
                        wlan0hasMac = true;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        if(isEth0 == true && eth0hasIp == true && eth0hasMac == true){
            return true;
        }

        return false;
    }
}
