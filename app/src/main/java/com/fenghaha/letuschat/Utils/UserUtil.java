package com.fenghaha.letuschat.Utils;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FengHaHa on2018/8/26 0026 2:30
 */
public class UserUtil {
    public static void getContactsList(BaseContract.BaseCallBack callBack) {
        try {
            AVQuery<AVUser> friendsQuery = AVUser.getCurrentUser().followerQuery(AVUser.class);
            friendsQuery.include("follower");
            friendsQuery.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null) {
                        callBack.onComplete();
                        MyApp.setFriendsList(list);
                        HashMap<String, AVUser> hashMap = new HashMap<>();
                        for (AVUser u :
                                list) {
                            hashMap.put(u.getObjectId(), u);
                        }
                        MyApp.setAvUserHashMap(hashMap);
                    } else {
                        callBack.onFailure(e.getMessage());
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public static void findUser(String userName, BaseContract.BaseCallBack<AVUser> callBack) {
        AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereEqualTo("username", userName);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null ){
                    if ( list.size() > 0)
                    callBack.onSuccess(list.get(0));
                    else callBack.onFailure("没有找到这个人哦！");
                }
                else callBack.onFailure(e.getMessage());
            }
        });
    }
}
