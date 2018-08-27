package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;
import com.fenghaha.letuschat.MVP.Model.ChatModel;
import com.fenghaha.letuschat.MVP.Presenter.ChatPresenter;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Adapter.Recycler.MessageRecAdapter;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.MyTextUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity implements ChatContract.ChatView {

    @BindView(R.id.rec_chat_message)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.tv_send_message)
    TextView mSendBtn;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.tv_toolbar_title)
    TextView mTitle;
    @BindView(R.id.iv_voice)
    ImageView ivVoice;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    private MessageRecAdapter mAdapter;
    private ChatPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initViews();
        initMvp();
    }

    @Override
    protected void initMvp() {
        Intent intent = getIntent();
        mPresenter = new ChatPresenter(new ChatModel(intent.getStringExtra("id")));
        mPresenter.attachView(this);
        mPresenter.registerMessageHandler();
        mPresenter.createConversation();
    }

    @Override
    protected void initViews() {
        mTitle.setText((String) MyApp.getAvUserHashMap().get(getIntent().getStringExtra("id")).get("nickname"));
        mAdapter = new MessageRecAdapter(message -> {
            if (message instanceof AVIMImageMessage){
                ShowPhotoActivity.actionStart(this,((AVIMImageMessage) message).getFileUrl());
            }

        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mSendBtn.setOnClickListener(v -> {
            String text = mEtInput.getText().toString();
            if (!MyTextUtil.isEmpty(text)) {
                AVIMTextMessage message = new AVIMTextMessage();
                message.setText(text);
                mPresenter.sendMessage(message);
                mEtInput.setText("");
            }
        });
        mBack.setOnClickListener(v -> finish());
        ivCamera.setOnClickListener(v -> PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .compress(true)
                .forResult(PictureConfig.CHOOSE_REQUEST));
        ivImage.setOnClickListener(v -> PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .imageSpanCount(3)
                .isCamera(true)
                .compress(true)
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    LocalMedia image = PictureSelector.obtainMultipleResult(data).get(0);
                    String path;
                    path = image.isCompressed() ? image.getCompressPath() : image.getPath();
                    sendImage(path);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

                    break;
            }
        }
    }

    private void sendImage(String path) {
        try {
            AVIMImageMessage picture = new AVIMImageMessage(path);
            mPresenter.sendMessage(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showNewMessage(AVIMMessage message) {
        mAdapter.addMessage(message);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void showMessageList(List<AVIMMessage> messageList) {
        mAdapter.addMessageList(messageList);
        mRecyclerView.scrollToPosition(messageList.size() - 1);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unRegisterMessageHandler();
        mPresenter.detachView();
    }

    public static void actionStart(Context context, String id) {
        context.startActivity(new Intent(context, ChatActivity.class).putExtra("id", id));
    }
}
