package com.fenghaha.letuschat.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.fenghaha.letuschat.MVP.Contract.ConversationContract;
import com.fenghaha.letuschat.MVP.Model.ConversationModel;
import com.fenghaha.letuschat.MVP.Presenter.ConversationPresenter;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Adapter.Recycler.ConversationRecAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;


/**
 * Created by FengHaHa on2018/8/22 0022 17:42
 */
public class ConversationFrag extends BaseFragment implements ConversationContract.ConversationView {
    @BindView(R.id.rec_message)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    RelativeLayout mTitleLayout;

    private ConversationRecAdapter mAdapter;
    private ConversationPresenter mPresenter;

    public ConversationFrag() {
    }

    @SuppressLint("ValidFragment")
    public ConversationFrag(RelativeLayout mTitleLayout) {
        this.mTitleLayout = mTitleLayout;
    }

    @Override
    public int getContentViewId() {
        return R.layout.frg_home_conversation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.requestConversation();
    }

    @Override
    protected void initMvp() {
        mPresenter = new ConversationPresenter(new ConversationModel());
        mPresenter.attachView(this);
        mPresenter.requestConversation();
        mPresenter.registerMessageHandler();
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ConversationRecAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        swipeLayout.setOnRefreshListener(() -> mPresenter.requestConversation());
    }

    @Override
    public void showConversation(AVIMConversation conversation) {
        mAdapter.addConversationList(Collections.singletonList(conversation));
    }

    @Override
    public void showConversationList(List<AVIMConversation> conversationList) {
        swipeLayout.setRefreshing(false);
        mAdapter.setConversationList(conversationList);
    }

    @Override
    public void showMessage(AVIMMessage message) {
        mAdapter.showMessage(message);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unRegisterMessageHandler();
        mPresenter.detachView();
    }
}
