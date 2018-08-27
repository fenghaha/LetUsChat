package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.avos.avoscloud.AVUser;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.MyTextUtil;
import com.fenghaha.letuschat.Utils.ChatUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.input_text_number)
    TextInputEditText inputTextNumber;
    @BindView(R.id.bt_find_person)
    Button btFindPerson;
    @BindView(R.id.bt_find_group)
    Button btFindGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        initViews();
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, AddFriendActivity.class));
    }

    @Override
    protected void initViews() {
        mBack.setOnClickListener(this);
        btFindPerson.setOnClickListener(this);
        btFindGroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_find_person:
                findPerson();
                break;
            case R.id.bt_find_group:
                break;
        }
    }

    private void findPerson() {
        if (MyTextUtil.isEmpty(inputTextNumber.getText().toString())){
            inputTextNumber.setError("输入不能为空！");
        }else {
            ChatUtil.findUser(inputTextNumber.getText().toString(), new BaseContract.BaseCallBack<AVUser>() {
                @Override
                public void onSuccess(AVUser data) {
                    MyApp.getAvUserHashMap().put(data.getObjectId(),data);
                    UserDetailActivity.actionStart(AddFriendActivity.this,data.getObjectId());
                }
            });
        }
    }
}
