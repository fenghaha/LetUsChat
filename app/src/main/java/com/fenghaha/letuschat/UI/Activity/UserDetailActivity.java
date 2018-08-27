package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.fenghaha.letuschat.Utils.MyApp;
import com.fenghaha.letuschat.Utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailActivity extends BaseActivity {

    @BindView(R.id.iv_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_info)
    TextView mInfo;
    @BindView(R.id.tv_number)
    TextView mNumber;
    @BindView(R.id.tv_motto)
    TextView mMotto;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_goto)
    TextView tvGoto;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.layout_goto)
    RelativeLayout mGoto;
    @BindView(R.id.padding_view)
    View paddingView;

    private boolean isSelf;
    private boolean isFriend;

    private AVUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        isSelf = AVUser.getCurrentUser().getObjectId().equals(getIntent().getStringExtra("id"));
        if (isSelf) mUser = AVUser.getCurrentUser();
        else {
            mUser = MyApp.getAvUserHashMap().get(getIntent().getStringExtra("id"));
        }
        isSelf = AVUser.getCurrentUser().getObjectId().equals(mUser.getObjectId());
        isFriend = MyApp.getFriendsList().contains(mUser);
        initViews();
    }


    @Override
    protected void initViews() {
        ImageLoader.loadImage(this, (String) mUser.get("avatarUrl"), mAvatar);
        mName.setText((String) mUser.get("nickname"));
        mNumber.setText(mUser.getUsername());
        mMotto.setText((String) mUser.get("motto"));
        mBack.setOnClickListener(v -> finish());
        mAvatar.setOnClickListener(v -> ShowPhotoActivity.actionStart(this, (String) AVUser.getCurrentUser().get("avatarUrl")));

        if (!isSelf) {
            if (!isFriend) showAddFriend();
            else showIsFriend();
        } else {
            showIsSelf();
        }
    }

    private void showIsSelf() {
        tvGoto.setText("编辑资料");
        ivIcon.setImageResource(R.drawable.ic_edit_02);
        mGoto.setOnClickListener(v -> UserInfoEditActivity.actionStart(this));
    }

    void showAddFriend() {
        tvGoto.setText("添加好友");
        ivIcon.setImageResource(R.drawable.ic_add_friend);
        mGoto.setOnClickListener(v -> addFriend());
    }

    void showIsFriend() {
        tvGoto.setText("发信息");
        ivIcon.setImageResource(R.drawable.ic_send_message);
        mGoto.setOnClickListener(v -> {
            ChatActivity.actionStart(this, mUser.getObjectId());
            finish();
        });
    }

    private void addFriend() {
        AVUser.getCurrentUser().followInBackground(mUser.getObjectId(), new FollowCallback() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
                    if (!MyApp.getAvUserHashMap().containsKey(mUser.getObjectId())) {
                        MyApp.getFriendsList().add(mUser);
                        isFriend = true;
                    }
                    ToastUtil.makeToast("添加成功！");
                } else if (e.getCode() == AVException.DUPLICATE_VALUE) {
                    ToastUtil.makeToast("你们已经是好友了！");
                }
            }
        });
    }

    public static void actionStart(Context context, String userID) {
        context.startActivity(new Intent(context, UserDetailActivity.class).putExtra("id", userID));
    }
}
