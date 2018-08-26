package com.fenghaha.letuschat.UI.Fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Adapter.Recycler.ContactsRecAdapter;
import com.fenghaha.letuschat.Utils.MyApp;

import butterknife.BindView;

/**
 * Created by FengHaHa on2018/8/24 0024 13:58
 */
public class ContactsItemFrag extends BaseFragment {
    @BindView(R.id.rec_contacts)
    RecyclerView recContacts;


    private ContactsRecAdapter mAdapter;
    //private ContactsPresenter mPresenter;
    int size;

    @Override
    public int getContentViewId() {
        return R.layout.item_frag_contacts;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (size < MyApp.getFriendsList().size())
            refresh();
    }

    @Override
    public void initViews() {
        mAdapter = new ContactsRecAdapter(mContext);
        recContacts.setLayoutManager(new LinearLayoutManager(mContext));
        recContacts.setAdapter(mAdapter);
        refresh();
    }

    private void refresh() {
        size = MyApp.getFriendsList().size();
        mAdapter.addContactList(MyApp.getFriendsList());
    }

//    @Override
//    protected void initMvp() {
//        mPresenter = new ContactsPresenter(new ContactsModel());
//        mPresenter.attachView(this);
//        mPresenter.requestFriendsList();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        mPresenter.detachView();
//    }

//    @Override
//    public void showContactsList(List<AVUser> userList) {
//        mAdapter.addContactList(userList);
//    }
}
