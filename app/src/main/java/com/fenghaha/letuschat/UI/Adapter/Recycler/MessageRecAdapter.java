package com.fenghaha.letuschat.UI.Adapter.Recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.fenghaha.letuschat.UI.Adapter.Recycler.ViewHolder.ChatBaseViewHolder;
import com.fenghaha.letuschat.UI.Adapter.Recycler.ViewHolder.ChatTextViewHolder;
import com.fenghaha.letuschat.Utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by FengHaHa on2018/8/22 0022 22:04
 */
public class MessageRecAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int ITEM_LEFT = 100;
    private final int ITEM_LEFT_TEXT = 101;
    private final int ITEM_LEFT_IMAGE = 102;
    private final int ITEM_LEFT_AUDIO = 103;
    private final int ITEM_LEFT_LOCATION = 104;

    private final int ITEM_RIGHT = 200;
    private final int ITEM_RIGHT_TEXT = 201;
    private final int ITEM_RIGHT_IMAGE = 202;
    private final int ITEM_RIGHT_AUDIO = 203;
    private final int ITEM_RIGHT_LOCATION = 204;

    private final int ITEM_UNKNOWN = 300;
    protected List<AVIMMessage> mMessageList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_LEFT:
            case ITEM_LEFT_TEXT:
                return new ChatTextViewHolder(parent.getContext(), parent, true);
            case ITEM_RIGHT:
            case ITEM_RIGHT_TEXT:
                return new ChatTextViewHolder(parent.getContext(), parent, false);
            default:
                return new ChatTextViewHolder(parent.getContext(), parent, true);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ((ChatBaseViewHolder) viewHolder).bindData(mMessageList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        AVIMMessage message = mMessageList.get(position);
        if (null != message && message instanceof AVIMTypedMessage) {
            AVIMTypedMessage typedMessage = (AVIMTypedMessage) message;
            boolean isMe = MessageUtil.isFromMe(typedMessage);
            if (typedMessage.getMessageType() == AVIMReservedMessageType.TextMessageType.getType()) {
                return isMe ? ITEM_RIGHT_TEXT : ITEM_LEFT_TEXT;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.AudioMessageType.getType()) {
                return isMe ? ITEM_RIGHT_AUDIO : ITEM_LEFT_AUDIO;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.ImageMessageType.getType()) {
                return isMe ? ITEM_RIGHT_IMAGE : ITEM_LEFT_IMAGE;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.LocationMessageType.getType()) {
                return isMe ? ITEM_RIGHT_LOCATION : ITEM_LEFT_LOCATION;
            } else {
                return isMe ? ITEM_RIGHT : ITEM_LEFT;
            }
        }
        return ITEM_UNKNOWN;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }


    public void addMessageList(List<AVIMMessage> messages) {
        mMessageList.clear();
        if (null != messages) {
            mMessageList.addAll(messages);
        }
        notifyDataSetChanged();
    }

    public void addMessage(AVIMMessage message) {
        mMessageList.add(message);
        notifyDataSetChanged();
    }
}
