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
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.fenghaha.letuschat.MVP.Contract.ChatContract;
import com.fenghaha.letuschat.MVP.Model.ChatModel;
import com.fenghaha.letuschat.MVP.Presenter.ChatPresenter;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Adapter.Recycler.MessageRecAdapter;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.MyTextUtil;

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
        mTitle.setText((String)MyApp.getAvUserHashMap().get(getIntent().getStringExtra("id")).get("nickname"));
        mAdapter = new MessageRecAdapter();
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
