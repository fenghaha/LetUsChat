package com.fenghaha.letuschat.MVP.Presenter;

import com.avos.avoscloud.AVUser;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.MVP.Contract.ContactsContract;
import com.fenghaha.letuschat.MVP.Model.ContactsModel;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/25 0025 2:50
 */
public class ContactsPresenter extends BasePresenter<ContactsContract.ContactsView> {
    private ContactsModel mModel;

    public ContactsPresenter(ContactsModel mModel) {
        this.mModel = mModel;
    }

    public void requestFriendsList() {
        mModel.getContactsList(new BaseContract.BaseCallBack<List<AVUser>>() {
            @Override
            public void onSuccess(List<AVUser> data) {
                mView.showContactsList(data);
            }
        });
    }
}
