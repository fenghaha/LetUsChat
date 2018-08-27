package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SaveCallback;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ChatUtil;
import com.fenghaha.letuschat.Utils.MyTextUtil;
import com.fenghaha.letuschat.Utils.TimerCallBack;
import com.fenghaha.letuschat.Utils.TimerUtil;
import com.fenghaha.letuschat.Utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.hint1)
    TextView hint1;
    @BindView(R.id.sms_layout)
    RelativeLayout smsLayout;
    @BindView(R.id.verify_code)
    EditText verifyCode;
    @BindView(R.id.tv_code_hint)
    TextView tvCodeHint;
    @BindView(R.id.ok_layout)
    RelativeLayout okLayout;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_psw)
    EditText etPsw;
    @BindView(R.id.nick_layout)
    RelativeLayout nickLayout;
    @BindView(R.id.hint_2)
    TextView hint2;
    @BindView(R.id.tv_show_username)
    TextView tvShowUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void initViews() {
        hint1.setText("输入手机号码");
        hint2.setText("目前仅支持中国大陆的手机号");
        smsLayout.setVisibility(View.VISIBLE);
        ivBack.setOnClickListener(v -> finish());
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (checkPhoneNumber(charSequence.toString())) {
                    btNext.setBackgroundResource(R.drawable.bg_blue);
                    btNext.setTextColor(Color.WHITE);
                    btNext.setClickable(true);
                } else {
                    btNext.setBackgroundResource(R.drawable.bg_gray);
                    btNext.setTextColor(Color.parseColor("#FFBABABA"));
                    btNext.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        verifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (MyTextUtil.isLegal(verifyCode.getText().toString(), 6, 6)) {
                    btNext.setBackgroundResource(R.drawable.bg_blue);
                    btNext.setTextColor(Color.WHITE);
                    btNext.setClickable(true);
                } else {
                    btNext.setBackgroundResource(R.drawable.bg_gray);
                    btNext.setTextColor(Color.parseColor("#FFBABABA"));
                    btNext.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (MyTextUtil.isLegal(etPsw.getText().toString(), 8, 16) && !MyTextUtil.isEmpty(etNickname.getText().toString())) {
                    btNext.setBackgroundResource(R.drawable.bg_blue);
                    btNext.setTextColor(Color.WHITE);
                    btNext.setClickable(true);
                } else {
                    btNext.setBackgroundResource(R.drawable.bg_gray);
                    btNext.setTextColor(Color.parseColor("#FFBABABA"));
                    btNext.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btNext.setOnClickListener(v -> getSmsCode(etPhone.getText().toString()));
    }

    boolean checkPhoneNumber(String phone) {
        String telRegex = "[1][3578]\\d{9}";
        return (phone.length() == 11 && phone.matches(telRegex));
    }

    private void getSmsCode(String phone) {

        if (!checkPhoneNumber(phone)) return;
        ChatUtil.hasRegisted(phone, new BaseContract.BaseCallBack<List<AVUser>>() {
            @Override
            public void onComplete() {
                AVOSCloud.requestSMSCodeInBackground(phone, new RequestMobileCodeCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            smsLayout.setVisibility(View.GONE);
                            okLayout.setVisibility(View.VISIBLE);
                            hint1.setText("请输入验证码");
                            hint2.setText("验证码已经由短信发送到手机号" + phone);
                            btNext.setText("下一步");
                            btNext.setOnClickListener(v -> verifySmsCode(phone));
                            TimerUtil.timer(new TimerCallBack() {
                                @Override
                                public void onTimerStarted() {
                                    tvCodeHint.setClickable(false);
                                }

                                @Override
                                public void onTimeChanged(int timeNow) {
                                    tvCodeHint.setText(String.valueOf(timeNow) + "秒后重新获取");
                                }

                                @Override
                                public void onFinish() {

                                    tvCodeHint.setClickable(true);
                                    tvCodeHint.setText("重新获取");
                                    tvCodeHint.setOnClickListener(v -> verifySmsCode(phone));

                                }
                            }, 60);
                        } else ToastUtil.makeToast(e.getMessage());

                    }


                });
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                ToastUtil.makeToast(msg);
            }
        });

    }


    private void verifySmsCode(String phone) {

        if (MyTextUtil.isLegal(verifyCode.getText().toString(), 6, 6)) {
            AVUser.signUpOrLoginByMobilePhoneInBackground(phone, verifyCode.getText().toString(), new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser user, AVException e) {
                    okLayout.setVisibility(View.GONE);
                    nickLayout.setVisibility(View.VISIBLE);
                    hint1.setText("设置昵称与密码");
                    hint2.setText(R.string.psw_hint);
                    btNext.setText("注册");
                    btNext.setOnClickListener(v -> setNicknameAndPassword(user));
                }
            });
        }
    }

    private void setNicknameAndPassword(AVUser user) {
        user.put("nickname", etNickname.getText().toString());
        String account = MyTextUtil.newRandomAccount();
        user.setUsername(account);
        user.setPassword(etPsw.getText().toString());
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                nickLayout.setVisibility(View.GONE);
                hint1.setText("注册成功！");
                hint2.setTextSize(18);
                hint2.setText("你的账号是：");
                tvShowUsername.setVisibility(View.VISIBLE);
                tvShowUsername.setText(account);
                btNext.setText("登陆");
                btNext.setOnClickListener(v -> LoginActivity.actionStart(RegisterActivity.this, account, etPsw.getText().toString()));

            }
        });
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }
}
