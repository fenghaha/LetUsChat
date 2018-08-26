package com.fenghaha.letuschat.UI.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.fenghaha.letuschat.MVP.Contract.LoginContract;
import com.fenghaha.letuschat.MVP.Model.LoginModel;
import com.fenghaha.letuschat.MVP.Presenter.LoginPresenter;
import com.fenghaha.letuschat.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity implements LoginContract.LoginView {

    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.tv_sign_up)
    TextView mSignUpBtn;
    @BindView(R.id.main_title)
    RelativeLayout mainTitle;
    @BindView(R.id.et_usr_name)
    EditText etUsrName;
    @BindView(R.id.et_usr_psw)
    EditText etUsrPsw;
    @BindView(R.id.et_usr_psw_repeat)
    EditText etUsrPswRepeat;
    @BindView(R.id.input_layout)
    LinearLayout mInputLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.main_btn)
    TextView mBtnLogin;
    @BindView(R.id.tv_find_psw)
    TextView tvFindPsw;
    @BindView(R.id.wrapper_view)
    RelativeLayout wrapperView;
    @BindView(R.id.layout_progress)
    LinearLayout layoutProgress;

    private LoginPresenter mPresenter;
    private boolean isLoginMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initViews();
        initMvp();
    }

    @Override
    protected void initViews() {
        mBack.setOnClickListener(v -> {
            if (isLoginMode) finish();
            else loginMode();
        });
        mBtnLogin.setOnClickListener(v -> {
            String usn = etUsrName.getText().toString();
            String psw = etUsrPsw.getText().toString();
            if (isLoginMode)
                mPresenter.login(usn, psw);
            else mPresenter.signUp(usn, psw, etUsrPswRepeat.getText().toString());
        });
        mSignUpBtn.setOnClickListener(v -> {
            isLoginMode = false;
            signUpMode();
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
        set.setDuration(500);
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
    }

    @Override
    public void showLoading() {
        // 隐藏输入框
        etUsrName.setVisibility(View.INVISIBLE);
        etUsrPsw.setVisibility(View.INVISIBLE);
        inputAnimator(layoutProgress.getMeasuredWidth(), layoutProgress.getMeasuredHeight());
    }

    @Override
    public void loginSuccess() {
        progressBar.setProgress(100, false);
        showToast("登录成功！");
        MainActivity.actionStart(this);
        finish();
    }

    @Override
    public void signUpSuccess(String usn, String psw) {
        showToast("注册成功！");
        loginMode();
        etUsrName.setText(usn);
        etUsrPsw.setText(psw);
    }

    @Override
    public void loginMode() {
        isLoginMode = true;
        wrapperView.setBackgroundColor(Color.parseColor("#7adfb8"));
        etUsrPswRepeat.setVisibility(View.GONE);
        mSignUpBtn.setVisibility(View.VISIBLE);
        mBtnLogin.setText("Login");
    }

    @Override
    public void signUpMode() {
        wrapperView.setBackgroundColor(Color.parseColor("#2fca8e"));
        etUsrPswRepeat.setVisibility(View.VISIBLE);
        mSignUpBtn.setVisibility(View.INVISIBLE);
        mBtnLogin.setText("SignUp");
    }

    @Override
    public void onBackPressed() {
        if (!isLoginMode) {
            loginMode();
            return;
        }
        super.onBackPressed();

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void recovery() {
        progressBar.setVisibility(View.INVISIBLE);
        mInputLayout.setVisibility(View.VISIBLE);
        etUsrName.setVisibility(View.VISIBLE);
        etUsrPsw.setVisibility(View.VISIBLE);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", mInputLayout.getScaleX(), 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }
}
