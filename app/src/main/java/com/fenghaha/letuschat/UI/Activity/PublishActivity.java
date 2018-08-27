package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVStatus;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.fenghaha.letuschat.Utils.ToastUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_image)
    ImageView ivImage;
    @BindView(R.id.iv_choose)
    ImageView ivChoose;
    @BindView(R.id.cancel_image)
    ImageView cancelImage;
    @BindView(R.id.answer_image_all)
    FrameLayout answerImageAll;
    @BindView(R.id.iv_send)
    ImageView ivSend;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        initViews();
    }

    @Override
    protected void initViews() {
        ivBack.setOnClickListener(v -> finish());
        answerImageAll.setOnClickListener(v -> openAlbum());
        cancelImage.setOnClickListener(v -> {
            Glide.with(this).clear(ivImage);
            cancelImage.setVisibility(View.GONE);
        });
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    ivSend.setClickable(true);
                    ivSend.setImageResource(R.drawable.ic_send_pre);
                } else {
                    ivSend.setClickable(false);
                    ivSend.setImageResource(R.drawable.ic_send);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ivSend.setOnClickListener(v -> publish());
    }

    private void publish() {
        try {
            AVFile file = AVFile.withAbsoluteLocalPath("avatar.png", path);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        String url = file.getUrl();
//                        Map<String, Object> status = new HashMap<String, Object>();
//
//                        status.put("name",AVUser.getCurrentUser().getUsername());
//                        status.put("avatar", AVUser.getCurrentUser().get("avatarUrl"));
//                        status.put("image", url);
//                        status.put("text", etContent.getText().toString());
//                        AVStatus statusa = AVStatus.createStatusWithData(status);
//                        statusa.setInboxType("system");
//
//                        statusa.sendInBackgroundWithBlock(new SaveCallback() {
//                            @Override
//                            public void done(AVException e) {
//                                ToastUtil.makeToast("发送成功！");
//                                finish();
//                            }
//                        });
//                        String url = file.getUrl();
                        AVStatus status = new AVStatus();
                        status.put("name",AVUser.getCurrentUser().getUsername());
                        status.put("avatar", AVUser.getCurrentUser().get("avatarUrl"));
                        status.put("image", url);
                        status.put("text", etContent.getText().toString());
//                        status.setInboxType("system");
//                        status.sendInBackground(new SaveCallback() {
//                            @Override
//                            public void done(AVException e) {
//                                ToastUtil.makeToast("发送成功！");
//                                finish();
//                            }
//                        });








                        AVStatus.sendStatusToFollowersInBackgroud(status, new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                ToastUtil.makeToast("发送成功！");
                                finish();
                            }
                        });
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openAlbum() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .imageSpanCount(3)
                .isCamera(true)
                .compress(true)
                .minimumCompressSize(100)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    LocalMedia image = PictureSelector.obtainMultipleResult(data).get(0);
                    path = image.isCompressed() ? image.getCompressPath() : image.getPath();
                    setImage();
                    break;
            }
        }
    }

    private void setImage() {
        cancelImage.setVisibility(View.VISIBLE);
        ivChoose.setVisibility(View.GONE);
        ImageLoader.loadImage(this, new File(path), ivImage);
    }

    public static void actionStart(Context context){
        context.startActivity(new Intent(context,PublishActivity.class));
    }
}
