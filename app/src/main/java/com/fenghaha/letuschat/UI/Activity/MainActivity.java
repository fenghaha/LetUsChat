package com.fenghaha.letuschat.UI.Activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.CustomView.CustomPopWindow;
import com.fenghaha.letuschat.UI.Fragment.CommunityFrag;
import com.fenghaha.letuschat.UI.Fragment.ContactsFrag;
import com.fenghaha.letuschat.UI.Fragment.ConversationFrag;
import com.fenghaha.letuschat.Utils.ImageLoader;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.nav_bottom)
    BottomNavigationView navBottom;
    @BindView(R.id.iv_more)
    ImageView mMore;
    @BindView(R.id.nav_left)
    NavigationView navLeft;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.profile_image)
    CircleImageView mAvatar;
    @BindView(R.id.tv_toolbar_title)
    TextView mTitle;
    @BindView(R.id.title_layout)
    RelativeLayout mTitleLayout;

    private CustomPopWindow popWindow;
    private View mMoreView;

    private Fragment[] mFragments;
    private int currentFragIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        initMvp();
    }

    @Override
    protected void initViews() {
        ImageLoader.loadImage(this, (String) AVUser.getCurrentUser().get("avatarUrl"), mAvatar);
        mFragments = new Fragment[3];
        mFragments[0] = new ConversationFrag(mTitleLayout);
        mFragments[1] = new ContactsFrag(mTitleLayout);
        mFragments[2] = new CommunityFrag(mTitleLayout);
        setDrawerLeftEdgeSize(mDrawerLayout);
        mMore.setOnClickListener(v -> showMoreWindow());
        mAvatar.setOnClickListener(v -> mDrawerLayout.openDrawer(Gravity.LEFT));
        switchFragment(0);
        navBottom.setOnNavigationItemSelectedListener(menuItem -> {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            switch (menuItem.getItemId()) {
                case R.id.navigation_message:
                    mTitle.setText("消息");
                    switchFragment(0);
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    return true;
                case R.id.navigation_contacts:
                    mTitle.setText("联系人");
                    switchFragment(1);
                    return true;
                case R.id.navigation_community:
                    mTitle.setText("动态");
                    switchFragment(2);
                    return true;
            }
            return false;
        });

        View view = navLeft.inflateHeaderView(R.layout.nav_header);
        CircleImageView mNavAvatar = view.findViewById(R.id.nav_header_avatar);
        TextView mUsername = view.findViewById(R.id.tv_nav_header_username);
        TextView mMotto = view.findViewById(R.id.tv_nav_header_motto);
        AVUser myself = AVUser.getCurrentUser();
        View myView = view.findViewById(R.id.myself_layout);
        myView.setOnClickListener(v->UserDetailActivity.actionStart(this,myself.getObjectId()));
        String nickname = (String) myself.get("nickname");
        String motto = (String) myself.get("motto");
        if (nickname != null) mUsername.setText(nickname);
        else mUsername.setText(myself.getUsername());
        if (motto != null) {
            mMotto.setText(motto);
            mMotto.setCompoundDrawables(null, null, null, null);
        }
        ImageLoader.loadImage(this, (String) myself.get("avatarUrl"), mNavAvatar);
        navLeft.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.nav_logout:
                    changeAccount();
                    return true;
                case R.id.nav_change_password:
                    ChangePasswordActivity.actionStart(this);
                    return true;
                case R.id.nav_exit:
                    finish();
                    return true;
            }
            return false;
        });


        mMoreView = LayoutInflater.from(this).inflate(R.layout.pop_more_layout, null);
        View view1 = mMoreView.findViewById(R.id.layout_wrapper_1);
        View view2 = mMoreView.findViewById(R.id.layout_wrapper_2);
        view2.setOnClickListener(v -> AddFriendActivity.actionStart(this));
    }

    private void showMoreWindow() {
        if (popWindow == null) {
            popWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(mMoreView)
                    .setFocusable(true)
                    .setOutsideTouchable(true)
                    .setOnDissmissListener(() -> {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1.0f;
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        getWindow().setAttributes(lp);
                    })
                    .create();
        }
        if (popWindow.isShowing()) popWindow.dismiss();
        else {
            popWindow.showAsDropDown(mMore, -285, 30);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.alpha = 0.82f;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            getWindow().setAttributes(lp);
        }
    }

    private void changeAccount() {
        AVUser.logOut();
        finish();
        LoginActivity.actionStart(this);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    private void setDrawerLeftEdgeSize(DrawerLayout drawerLayout) {
        if (drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField;//Right
            leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            // 设置新的边缘大小
            Point displaySize = new Point();
            getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, (int) Math.max(edgeSize, (displaySize.x) * 0.3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void switchFragment(int newIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragIndex >= 0) transaction.hide(mFragments[currentFragIndex]);
        if (!mFragments[newIndex].isAdded()) {
            transaction.add(R.id.home_frg_wrapper, mFragments[newIndex]);
        }
        transaction.show(mFragments[newIndex]).commit();
        currentFragIndex = newIndex;
    }

    /*
     *按返回键返回桌面
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
