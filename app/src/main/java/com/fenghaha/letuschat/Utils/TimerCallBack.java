package com.fenghaha.letuschat.Utils;

/**
 * Created by FengHaHa on2018/8/27 0027 18:48
 */
public interface TimerCallBack {
    void onTimerStarted();
    void onTimeChanged(int timeNow);
    void onFinish();

}
