package com.fenghaha.letuschat.MVP.Contract;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/25 0025 23:21
 */
public class ConversationContract extends BaseContract {
    public interface ConversationView extends BaseView {
        void showConversation(AVIMConversation conversation);

        void showConversationList(List<AVIMConversation> conversationList);

        void showMessage(AVIMMessage message);
    }
}
