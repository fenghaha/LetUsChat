package com.fenghaha.letuschat.MVP.Model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.Utils.MyApp;

import java.util.HashMap;
import java.util.List;

/**
 * Created by FengHaHa on2018/8/25 0025 2:44
 */
public class ContactsModel {
    public void getContactsList(BaseContract.BaseCallBack<List<AVUser>> callBack) {
        try {
            AVQuery<AVUser> friendsQuery = AVUser.getCurrentUser().followerQuery(AVUser.class);
            friendsQuery.include("follower");
            friendsQuery.findInBackground(new FindCallback<AVUser>() {
                @Override
                public void done(List<AVUser> list, AVException e) {
                    if (e == null) {
                        callBack.onSuccess(list);
                        HashMap<String, AVUser> hashMap = new HashMap<>();
                        for (AVUser u :
                                list) {

                            hashMap.put(u.getObjectId(), u);
                        }
                        MyApp.setAvUserHashMap(hashMap);
                    } else callBack.onFailure(e.getMessage());
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }
}
