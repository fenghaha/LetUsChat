package com.fenghaha.letuschat.MVP.Contract;

import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * Created by FengHaHa on2018/8/25 0025 2:39
 */
public class ContactsContract extends BaseContract{
    public interface ContactsView extends BaseView{
        void showContactsList(List<AVUser> userList);
    }
}
