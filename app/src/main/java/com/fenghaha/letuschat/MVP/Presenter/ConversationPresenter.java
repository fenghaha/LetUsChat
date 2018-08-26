package com.fenghaha.letuschat.MVP.Presenter;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;
import com.fenghaha.letuschat.MVP.Contract.ConversationContract;
import com.fenghaha.letuschat.MVP.Model.ConversationModel;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/25 0025 23:35
 */
public class ConversationPresenter extends BasePresenter<ConversationContract.ConversationView> {
    private ConversationModel mModel;

    public ConversationPresenter(ConversationModel mModel) {
        this.mModel = mModel;
    }
    public void requestConversation(){
      mModel.getConversationList(new BaseContract.BaseCallBack<List<AVIMConversation>>() {
          @Override
          public void onSuccess(List<AVIMConversation> data) {
             mView.showConversationList(data);
          }
      });
    }
    public void registerMessageHandler(){
        mModel.registerMessageHandler(new ChatContract.ChatCallBack() {
            @Override
            public void onReceiveMessage(AVIMMessage message) {
                mView.showMessage(message);
            }
        });
    }
    public void unRegisterMessageHandler(){
        mModel.unRegisterMessageHandler();
    }
}
