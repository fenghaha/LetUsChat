package com.fenghaha.letuschat.UI.Adapter.Recycler.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.fenghaha.letuschat.Utils.MyTextUtil;
import com.othershe.library.NiceImageView;

import java.io.File;

/**
 * Created by FengHaHa on2018/8/27 0027 1:25
 */
public class ChatImageViewHolder extends ChatBaseViewHolder {
    private static final int MAX_DEFAULT_HEIGHT = 500;
    private static final int MAX_DEFAULT_WIDTH = 400;
    private NiceImageView contentView;

    public ChatImageViewHolder(Context context, ViewGroup root, boolean isLeft) {
        super(context, root, isLeft);
    }

    @Override
    protected void initView() {
        super.initView();
        conventLayout.addView(View.inflate(getContext(), R.layout.item_chat_image_layout, null));
        contentView = itemView.findViewById(R.id.chat_image_iv_content);
//        if (isLeft) contentView.setBackgroundResource(R.drawable.chat_left_bubble_nor);
//        else contentView.setBackgroundResource(R.drawable.chat_right_bubble_nor);
        contentView.setOnClickListener(v -> {
            clickListener.OnClick(message);
        });
    }

    @Override
    public void bindData(Object o) {
        super.bindData(o);
        AVIMMessage message = (AVIMMessage) o;
        if (message instanceof AVIMImageMessage) {
            AVIMImageMessage imageMessage = (AVIMImageMessage) message;
            String localPath = imageMessage.getLocalFilePath();
            String url = imageMessage.getFileUrl();
            // 图片的真实高度与宽度
            double actualHeight = imageMessage.getHeight();
            double actualWidth = imageMessage.getWidth();


            double viewHeight = MAX_DEFAULT_HEIGHT;
            double viewWidth = MAX_DEFAULT_WIDTH;
            if (0 != actualHeight && 0 != actualWidth) {
                // 要保证图片的长宽比不变
                double ratio = actualHeight / actualWidth;
                if (ratio > viewHeight / viewWidth) {
                    viewHeight = (actualHeight > viewHeight ? viewHeight : actualHeight);
                    viewWidth = viewHeight / ratio;
                } else {
                    viewWidth = (actualWidth > viewWidth ? viewWidth : actualWidth);
                    viewHeight = viewWidth * ratio;
                }
            }
            contentView.getLayoutParams().height = (int) viewHeight;
            contentView.getLayoutParams().width = (int) viewWidth;
            if (!MyTextUtil.isEmpty(localPath)) {
                ImageLoader.loadImage(getContext(),new File(localPath),contentView);
            }else if (!MyTextUtil.isEmpty(url)){
                ImageLoader.loadImage(getContext(),url,contentView);
            }
//            ImageLoader.loadImage(getContext(),imageMessage.getFileUrl(),contentView);
        }
    }
}
