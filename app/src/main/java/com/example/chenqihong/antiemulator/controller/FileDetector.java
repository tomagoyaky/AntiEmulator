package com.example.chenqihong.antiemulator.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class FileDetector {
    private List<String> dirList = new ArrayList<>();

    public void addDir(String dirPath){
        dirList.add(dirPath);
    }

    public boolean isEmulator(){
        Iterator<String> i = dirList.iterator();
        boolean result = false;
        while(i.hasNext()){
            result |= readFiles(i.next());
        }

        return result;
    }

    private boolean readFiles(String dirPath){
        File dir = new File(dirPath);
        boolean result = false;
        if(dir.isFile()){
            return matchEmulator(dir.getName());
        }

        String[] fileNames = dir.list();
        if(null == fileNames){
            return false;
        }

        for(int i = 0; i < fileNames.length; i++){
            File file = new File(fileNames[i]);
            if(file.isDirectory()){
                result |= readFiles(file.getAbsolutePath());
            }else{
                result |= matchEmulator(file.getName());
            }
        }

        return result;
    }

    private boolean matchEmulator(String fileName){
        boolean result = false;
        result = matching(fileName, Emulator.QEMU_TAG)
                |matching(fileName, Emulator.BLUESTACK_TAG)
                |matching(fileName, Emulator.GENYMOTION_TAG)
                |matching(fileName, Emulator.HAIMAWAN_TAG);
        return result;
    }

    private boolean matching(String fileName, String[] tags){
        for(int i = 0; i < tags.length; i ++){
            if(fileName.contains(tags[i])){
                return true;
            }
        }

        return false;
    }
}