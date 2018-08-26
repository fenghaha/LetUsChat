package com.fenghaha.letuschat.Utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FengHaHa on2018/8/20 0020 18:16
 */
public class MyApp extends Application {
    private static Context context;
    private static AVIMClient currentClient;
    private static HashMap<String, AVUser> avUserHashMap;
    private static final String TAG = "MyApp";
    private static List<AVUser> friendsList;



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "0OTaGxVPlR2UQAPS9BUQqxPs-gzGzoHsz", "Utys5EcOAC16PUlJwPf9fQSn");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        currentClient.close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {

            }
        });
    }

    public static void setFriendsList(List<AVUser> friendsList) {
        MyApp.friendsList = friendsList;
    }

    public static List<AVUser> getFriendsList() {
        return friendsList;
    }
    public static void openIMClient() {
        currentClient = AVIMClient.getInstance(AVUser.getCurrentUser());
        currentClient.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    Log.d(TAG, "成功开启连接: ");
                }
            }
        });
    }

    public static HashMap<String, AVUser> getAvUserHashMap() {
        return avUserHashMap;
    }

    public static void setAvUserHashMap(HashMap<String, AVUser> avUserHashMap) {
        MyApp.avUserHashMap = avUserHashMap;
    }

    public static AVIMClient getCurrentClient() {
        return currentClient;
    }


    public static Context getContext() {
        return context;
    }
}
