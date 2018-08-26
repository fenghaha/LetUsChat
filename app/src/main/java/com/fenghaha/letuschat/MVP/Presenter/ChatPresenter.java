package com.fenghaha.letuschat.MVP.Presenter;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;
import com.fenghaha.letuschat.MVP.Model.ChatModel;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/23 0023 21:10
 */
public class ChatPresenter extends BasePresenter<ChatContract.ChatView> {
    private ChatModel mModel;

    public ChatPresenter(ChatModel mModel) {
        this.mModel = mModel;
    }

    public void registerMessageHandler() {
        mModel.registerMessageHandler(new ChatContract.ChatCallBack() {
            @Override
            public void onReceiveMessage(AVIMMessage message) {
                mView.showNewMessage(message);
            }
        });
    }

    public void unRegisterMessageHandler() {
        mModel.unRegisterMessageHandler();
    }

    public void createConversation() {
        mModel.createConversation(() -> mModel.getMessageHistory(new BaseContract.BaseCallBack<List<AVIMMessage>>() {
            @Override
            public void onSuccess(List<AVIMMessage> data) {
                mView.showMessageList(data);
            }

        }));
    }

    public void sendMessage(AVIMMessage message) {
        mModel.sendMessage(message, new BaseContract.BaseCallBack<AVIMMessage>() {
            @Override
            public void onSuccess(AVIMMessage data) {
                mView.showNewMessage(data);
            }
        });
    }
}
