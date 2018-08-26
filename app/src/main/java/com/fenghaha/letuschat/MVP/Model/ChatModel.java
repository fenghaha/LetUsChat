package com.fenghaha.letuschat.MVP.Model;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.MyMessageHandler;

import java.util.Collections;
import java.util.List;

/**
 * Created by FengHaHa on2018/8/23 0023 20:22
 */
public class ChatModel {
    private boolean isConversationCreated = false;
    private AVIMConversation conversation;
    private MyMessageHandler messageHandler;
    private String id;


    public ChatModel(String id) {
        this.id = id;
    }

    public void registerMessageHandler(ChatContract.ChatCallBack mCallBack) {
        messageHandler = new MyMessageHandler(new ChatContract.ChatCallBack() {
            @Override
            public void onReceiveMessage(AVIMMessage message) {
                if (message.getConversationId().equals(conversation.getConversationId()))
                    mCallBack.onReceiveMessage(message);
            }
        });
        AVIMMessageManager.registerMessageHandler(AVIMMessage.class, messageHandler);
    }

    public void unRegisterMessageHandler() {
        AVIMMessageManager.unregisterMessageHandler(AVIMMessage.class, messageHandler);
    }

    public void createConversation(ConversationCreateListener listener) {
        MyApp.getCurrentClient().createConversation(
                Collections.singletonList(id), "", null, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        if (e == null) {
                            isConversationCreated = true;
                            conversation = avimConversation;
                            if (listener!=null)listener.onCreated();
                        }
                    }
                });
    }

    public void sendMessage(AVIMMessage message, BaseContract.BaseCallBack<AVIMMessage> callBack) {
        if (!isConversationCreated) {
            createConversation(null);
            callBack.onFailure("请等待连接建立");
            return;
        }
        AVIMTextMessage textMessage = (AVIMTextMessage) message;
        conversation.set("lastMessage", textMessage.getText());
        conversation.updateInfoInBackground(new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {

            }
        });
        conversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) callBack.onSuccess(message);
                else {
                    callBack.onError(e);
                    callBack.onFailure(e.getMessage());
                }
            }
        });
    }


    public void getMessageHistory(BaseContract.BaseCallBack<List<AVIMMessage>> callBack) {
        if (!isConversationCreated){
            callBack.onFailure("");
            return;
        }
        conversation.queryMessages(20, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (e == null) callBack.onSuccess(list);
                else callBack.onFailure(e.getMessage());
            }
        });
    }
    public interface ConversationCreateListener {
        void onCreated();
    }
}
