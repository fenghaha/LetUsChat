package com.fenghaha.letuschat.Utils;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by FengHaHa on2018/8/27 0027 18:50
 * 倒计时
 */
public class TimerUtil {
    private static Handler handler = new Handler();

    public static void timer(TimerCallBack callBack, int period) {
        handler.post(callBack::onTimerStarted);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int p = period + 1;

            @Override
            public void run() {
                p--;
                if (p == 0) {
                    handler.post(callBack::onFinish);
                    timer.cancel();
                }else {
                    handler.post(() -> callBack.onTimeChanged(p));
                }

            }
        }, 0, 1000);
        // 发送失败可以查看 e 里面提供的信息
    }
}
