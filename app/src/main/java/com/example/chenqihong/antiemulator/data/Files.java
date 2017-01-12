package com.example.chenqihong.antiemulator.data;

/**
 * Created by chenqihong on 2017/1/12.
 */

public class Files {
    private boolean isPipeMatched;
    private boolean isFileMatched;
    private boolean isDirMatched;

    public boolean isPipeMatched() {
        return isPipeMatched;
    }

    public void setPipeMatched(boolean pipeMatched) {
        isPipeMatched = pipeMatched;
    }

    public boolean isFileMatched() {
        return isFileMatched;
    }

    public void setFileMatched(boolean fileMatched) {
        isFileMatched = fileMatched;
    }

    public boolean isDirMatched() {
        return isDirMatched;
    }

    public void setDirMatched(boolean dirMatched) {
        isDirMatched = dirMatched;
    }
}
