package com.fenghaha.letuschat.UI.Adapter.Recycler.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Activity.UserDetailActivity;
import com.fenghaha.letuschat.Utils.DateUtil;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.SimpleClickListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FengHaHa on2018/8/23 0023 14:52
 * 聊天界面的基本ViewHolder 用于各种类型（文字 语音 图片）的ViewHolder继承
 */
public class ChatBaseViewHolder extends BaseViewHolder {
    protected boolean isLeft;
    protected AVIMMessage message;
    protected SimpleClickListener clickListener;
    protected CircleImageView avatarView;
    protected TextView timeView;
    protected TextView nameView;
    protected LinearLayout conventLayout;
    protected FrameLayout statusLayout;
    protected ProgressBar progressBar;
    protected TextView statusView;
    protected ImageView errorView;

    public ChatBaseViewHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft ? R.layout.item_chat_left_layout : R.layout.item_chat_right_layout);
        this.isLeft = isLeft;
        initView();
    }

    public ChatBaseViewHolder setClickListener(SimpleClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    protected void initView() {
        if (isLeft) {
            avatarView = itemView.findViewById(R.id.chat_left_iv_avatar);
            timeView = itemView.findViewById(R.id.chat_left_tv_time);
            nameView = itemView.findViewById(R.id.chat_left_tv_name);
            conventLayout = itemView.findViewById(R.id.chat_left_layout_content);
            statusLayout = itemView.findViewById(R.id.chat_left_layout_status);
            statusView = itemView.findViewById(R.id.chat_left_tv_status);
            progressBar = itemView.findViewById(R.id.chat_left_progressbar);
            errorView = itemView.findViewById(R.id.chat_left_tv_error);
        } else {
            avatarView = itemView.findViewById(R.id.chat_right_iv_avatar);
            timeView = itemView.findViewById(R.id.chat_right_tv_time);
            nameView = itemView.findViewById(R.id.chat_right_tv_name);
            conventLayout = itemView.findViewById(R.id.chat_right_layout_content);
            statusLayout = itemView.findViewById(R.id.chat_right_layout_status);
            progressBar = itemView.findViewById(R.id.chat_right_progressbar);
            errorView = itemView.findViewById(R.id.chat_right_tv_error);
            statusView = itemView.findViewById(R.id.chat_right_tv_status);
        }
//        setAvatarClickEvent();
//        setResendClickEvent();
//        setUpdateMessageEvent();
    }


    @Override
    public void bindData(Object o) {
        message = (AVIMMessage) o;
        if (!avatarView.hasOnClickListeners()) {
            avatarView.setOnClickListener(v -> UserDetailActivity.actionStart(getContext(), message.getFrom()));

        }
        timeView.setText(DateUtil.millisecsToDateString(message.getTimestamp()));
        nameView.setText("");
        if (!isLeft) {
            ImageLoader.loadImage(getContext(), (String) AVUser.getCurrentUser().get("avatarUrl"), avatarView);
        } else {
            AVUser user = MyApp.getAvUserHashMap().get(message.getFrom());
            if (user != null)
                ImageLoader.loadImage(getContext(), (String) user.get("avatarUrl"), avatarView);
        }


        switch (message.getMessageStatus()) {
            case AVIMMessageStatusFailed:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.VISIBLE);
                break;
            case AVIMMessageStatusSent:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case AVIMMessageStatusSending:
                statusLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                errorView.setVisibility(View.GONE);
                break;
            case AVIMMessageStatusNone:
            case AVIMMessageStatusReceipt:
                statusLayout.setVisibility(View.GONE);
                break;
        }
    }
}
