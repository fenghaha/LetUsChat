package com.fenghaha.letuschat.Utils;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;

/**
 * Created by FengHaHa on2018/8/23 0023 21:31
 * 处理收到的消息
 */
public class MyMessageHandler extends AVIMMessageHandler {
    private ChatContract.ChatCallBack chatCallBack;

    public MyMessageHandler(ChatContract.ChatCallBack chatCallBack) {
        this.chatCallBack = chatCallBack;
    }

    @Override
    public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        super.onMessage(message, conversation, client);
        chatCallBack.onReceiveMessage(message);
    }
}
