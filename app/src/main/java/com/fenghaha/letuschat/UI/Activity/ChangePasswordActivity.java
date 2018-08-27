package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.MyTextUtil;
import com.fenghaha.letuschat.Utils.TimerCallBack;
import com.fenghaha.letuschat.Utils.TimerUtil;
import com.fenghaha.letuschat.Utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.tv_code_hint)
    TextView tvCodeHint;
    @BindView(R.id.et_new_psw)
    EditText etNewPsw;
    @BindView(R.id.main_btn)
    TextView mainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        initViews();
    }

    boolean checkPhoneNumber(String phone) {
        String telRegex = "[1][3578]\\d{9}";
        return (phone.length() == 11 && phone.matches(telRegex));
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ChangePasswordActivity.class));
    }

    @Override
    protected void initViews() {
        mainBtn.setClickable(false);
        ivBack.setOnClickListener(v -> finish());
        tvCodeHint.setOnClickListener(v -> {
            if (checkPhoneNumber(etPhoneNumber.getText().toString())) {
                requestSmsCode(etPhoneNumber.getText().toString());
            } else ToastUtil.makeToast("请正确输入手机号!");
        });
        etNewPsw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (MyTextUtil.isLegal(charSequence.toString(), 8, 16) &&
                        MyTextUtil.isLegal(etVerifyCode.getText().toString(), 6, 6)) {
                    mainBtn.setBackgroundResource(R.drawable.text_bg);
                    mainBtn.setClickable(true);
                } else {
                    mainBtn.setClickable(false);
                    mainBtn.setBackgroundResource(R.drawable.text_bg_gray);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mainBtn.setOnClickListener(v -> resetPassword(etVerifyCode.getText().toString(), etNewPsw.getText().toString()));
    }

    void requestSmsCode(String phone) {
        AVUser.requestPasswordResetBySmsCodeInBackground(phone, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    TimerUtil.timer(new TimerCallBack() {
                        @Override
                        public void onTimerStarted() {
                            tvCodeHint.setClickable(false);
                        }

                        @Override
                        public void onTimeChanged(int timeNow) {
                            tvCodeHint.setText(String.valueOf(timeNow) + "秒后可重新发送");
                        }

                        @Override
                        public void onFinish() {
                            tvCodeHint.setText("点击重新发送");
                            tvCodeHint.setClickable(true);
                            tvCodeHint.setOnClickListener(v -> requestSmsCode(phone));
                        }
                    }, 60);
                    ToastUtil.makeToast("验证码已发送");
                }else   ToastUtil.makeToast(e.getMessage());
            }
        });
    }

    void resetPassword(String code, String password) {
        if (!MyTextUtil.isLegal(password, 8, 16) ||
                !MyTextUtil.isLegal(code, 6, 6)){
           return;
        }
        AVUser.resetPasswordBySmsCodeInBackground(code, password, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    ToastUtil.makeToast("修改成功！");
                    LoginActivity.actionStart(ChangePasswordActivity.this,AVUser.getCurrentUser().getUsername(),password);
                    finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
