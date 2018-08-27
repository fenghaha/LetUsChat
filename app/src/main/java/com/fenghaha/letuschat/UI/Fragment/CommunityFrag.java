package com.fenghaha.letuschat.UI.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVStatusQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.InboxStatusFindCallback;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Activity.PublishActivity;
import com.fenghaha.letuschat.UI.Adapter.Recycler.StatusRecAdapter;
import com.fenghaha.letuschat.Utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by FengHaHa on2018/8/24 0024 0:40
 */
public class CommunityFrag extends BaseFragment {
    RelativeLayout mTitleLayout;
    @BindView(R.id.rec_main)
    RecyclerView recMain;

    ImageView ivMore;
    private StatusRecAdapter mAdapter;

    public CommunityFrag() {
    }

    @SuppressLint("ValidFragment")
    public CommunityFrag(RelativeLayout mTitleLayout) {
        this.mTitleLayout = mTitleLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getStatusList();
    }

    private void getStatusList() {
        AVStatusQuery inboxQuery = AVStatus.inboxQuery(AVUser.getCurrentUser(), AVStatus.INBOX_TYPE.TIMELINE.toString());
        inboxQuery.setLimit(50);  //设置最多返回 50 条状态
        inboxQuery.setSinceId(0);  //查询返回的 status 的 messageId 必须大于 sinceId，默认为 0
        inboxQuery.findInBackground(new InboxStatusFindCallback() {
            @Override
            public void done(final List<AVStatus> avObjects, final AVException e) {

                if (e==null)mAdapter.setStatusList(avObjects);
                else ToastUtil.makeToast("拉取动态失败");
            }
        });
    }

    @Override
    public void initViews() {
        ivMore = mTitleLayout.findViewById(R.id.iv_more);
        ivMore.setOnClickListener(v -> PublishActivity.actionStart(mContext));
        mAdapter = new StatusRecAdapter(getContext());
        recMain.setLayoutManager(new LinearLayoutManager(getContext()));
        recMain.setAdapter(mAdapter);
    }


    @Override
    public int getContentViewId() {
        return R.layout.frg_home_community;
    }

}
