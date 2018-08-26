package com.fenghaha.letuschat.UI.Adapter.Recycler.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.fenghaha.letuschat.R;

import java.util.HashMap;


/**
 * Created by FengHaHa on2018/8/23 0023 14:49
 */
public class ChatTextViewHolder extends ChatBaseViewHolder {
    protected TextView contentView;


    public ChatTextViewHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft);
    }

    @Override
    public void initView() {
        super.initView();
        if (isLeft) {
            conventLayout.addView(View.inflate(getContext(), R.layout.item_chat_left_text_layout, null));
            contentView = itemView.findViewById(R.id.chat_left_text_tv_content);
        } else {
            conventLayout.addView(View.inflate(getContext(), R.layout.item_chat_right_text_layout, null));
            contentView = itemView.findViewById(R.id.chat_right_text_tv_content);
        }
    }
    @Override
    public void bindData(Object o) {
        super.bindData(o);
        AVIMMessage message = (AVIMMessage) o;
        if (message instanceof AVIMTextMessage) {
            AVIMTextMessage textMessage = (AVIMTextMessage) message;
            contentView.setText(textMessage.getText());
        }
    }
}
