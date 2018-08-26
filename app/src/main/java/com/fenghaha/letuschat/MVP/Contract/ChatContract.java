package com.fenghaha.letuschat.MVP.Contract;

import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/23 0023 20:19
 */
public class ChatContract extends BaseContract{
   public interface ChatView extends BaseView{
        void showNewMessage(AVIMMessage message);
        void showMessageList(List<AVIMMessage> messageList);
    }
    public abstract static class ChatCallBack extends BaseCallBack{
      public abstract void onReceiveMessage(AVIMMessage message);
    }
}
