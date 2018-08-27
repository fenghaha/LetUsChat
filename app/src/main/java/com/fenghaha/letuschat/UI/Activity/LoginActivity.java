package com.fenghaha.letuschat.UI.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fenghaha.letuschat.MVP.Contract.LoginContract;
import com.fenghaha.letuschat.MVP.Model.LoginModel;
import com.fenghaha.letuschat.MVP.Presenter.LoginPresenter;
import com.fenghaha.letuschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements LoginContract.LoginView {


    @BindView(R.id.et_usr_name)
    EditText etUsrName;
    @BindView(R.id.et_usr_psw)
    EditText etUsrPsw;

    @BindView(R.id.input_layout)
    LinearLayout mInputLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.main_btn)
    TextView mBtnLogin;

    @BindView(R.id.wrapper_view)
    RelativeLayout wrapperView;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private LoginPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
        initMvp();
    }

    @Override
    protected void initViews() {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        if (!username.equals("0") && !password.equals("0")) {
            etUsrName.setText(username);
            etUsrPsw.setText(password);
        }
        tvRegister.setOnClickListener(v -> RegisterActivity.actionStart(this));
        mBtnLogin.setOnClickListener(v -> {
            String usn = etUsrName.getText().toString();
            String psw = etUsrPsw.getText().toString();
            mPresenter.login(usn, psw);
        });

    }

    @Override
    protected void initMvp() {
        mPresenter = new LoginPresenter(new LoginModel());
        mPresenter.attachView(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    private void inputAnimator(int w, int h) {
        AnimatorSet set = new AnimatorSet();
        float t = w / (float) mInputLayout.getMeasuredWidth();
        ObjectAnimator animator = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, t);
        set.setDuration(300);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator);
        set.start();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                mInputLayout.setVisibility(View.INVISIBLE);
                layoutProgress.setVisibility(View.VISIBLE);
            }
        });
        new Handler().postDelayed(() -> {
            showToast("登录成功！");
            MainActivity.actionStart(this);
            finish();
        }, 200);
    }

    @Override
    public void loginSuccess() {
        // 隐藏输入框
        etUsrName.setVisibility(View.INVISIBLE);
        etUsrPsw.setVisibility(View.INVISIBLE);
        inputAnimator(layoutProgress.getMeasuredWidth(), layoutProgress.getMeasuredHeight());
        progressBar.setProgress(100, false);
    }

    @Override
    public Context getContext() {
        return this;
    }


    public static void actionStart(Context context, String usm, String psw) {
        context.startActivity(new Intent(context, LoginActivity.class).putExtra("username", usm).putExtra("password", psw));

    }
}
