package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.fenghaha.letuschat.Utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoEditActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.et_nickname)
    EditText etNickname;
    @BindView(R.id.et_gender)
    EditText etGender;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_job)
    EditText etJob;
    @BindView(R.id.et_school)
    EditText etSchool;
    @BindView(R.id.et_motto)
    EditText etMotto;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    String avatarPath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        ButterKnife.bind(this);
        initViews();
    }


    private void chooseAvatar() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .enableCrop(true)
                .imageSpanCount(3)
                .isCamera(true)
                .freeStyleCropEnabled(true)
                .showCropFrame(false)
                .circleDimmedLayer(true)
                .openClickSound(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void saveAll() {
        AVUser user = AVUser.getCurrentUser();
        user.put("nickname", etNickname.getText().toString());
        user.put("age", etAge.getText().toString());
        user.put("gender", etGender.getText().toString());
        user.put("job", etJob.getText().toString());
        user.put("motto", etMotto.getText().toString());
        saveAvatar();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    LocalMedia image = PictureSelector.obtainMultipleResult(data).get(0);
                    String path;
                    path = image.getCutPath();
                    avatarPath = path;
                    ImageLoader.loadImage(this, new File(path), ivAvatar);
                    tvFinish.setTextColor(Color.parseColor("#ffffff"));
                    tvFinish.setClickable(true);
                    break;
            }
        }
    }

    private void saveAvatar() {
        if (avatarPath == null) {
            AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        ToastUtil.makeToast("修改成功!");
                        finish();
                    }
                }
            });
        } else {
            try {
                AVFile file = AVFile.withAbsoluteLocalPath("avatar.png", avatarPath);
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            AVUser.getCurrentUser().put("avatarUrl", file.getUrl());
                            AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    if (e == null) {
                                        ToastUtil.makeToast("修改成功!");
                                        finish();
                                    }
                                }
                            });

                        }
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, UserInfoEditActivity.class));
    }

    @Override
    protected void initViews() {
        ImageLoader.loadImage(this, (String) AVUser.getCurrentUser().get("avatarUrl"),ivAvatar);
        ivBack.setOnClickListener(v -> finish());
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    tvFinish.setTextColor(Color.parseColor("#ffffff"));
                    tvFinish.setClickable(true);
                } else {
                    tvFinish.setTextColor(Color.parseColor("#88d8f5"));
                    tvFinish.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        etNickname.addTextChangedListener(watcher);
        etAge.addTextChangedListener(watcher);
        etGender.addTextChangedListener(watcher);
        etJob.addTextChangedListener(watcher);
        etMotto.addTextChangedListener(watcher);
        tvFinish.setOnClickListener(v -> saveAll());
        ivAvatar.setOnClickListener(v -> chooseAvatar());
    }
}
