package com.fenghaha.letuschat.MVP.Model;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationsQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.MyMessageHandler;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/25 0025 23:23
 */
public class ConversationModel {
    private MyMessageHandler messageHandler;

    public void getConversationList(BaseContract.BaseCallBack<List<AVIMConversation>> callBack) {
        AVIMConversationsQuery query = MyApp.getCurrentClient().getConversationsQuery();
        query.limit(20);
        query.setWithLastMessagesRefreshed(true);
        query.findInBackground(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) callBack.onSuccess(list);
                else {
                    e.printStackTrace();
                    callBack.onFailure(e.getMessage());
                }
            }
        });
    }

    public void registerMessageHandler(ChatContract.ChatCallBack mCallBack) {
        messageHandler = new MyMessageHandler(new ChatContract.ChatCallBack() {
            @Override
            public void onReceiveMessage(AVIMMessage message) {
                mCallBack.onReceiveMessage(message);
            }
        });
        AVIMMessageManager.registerMessageHandler(AVIMMessage.class, messageHandler);
    }

    public void unRegisterMessageHandler() {
        AVIMMessageManager.unregisterMessageHandler(AVIMMessage.class, messageHandler);
    }
}
