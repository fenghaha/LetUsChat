package com.fenghaha.letuschat.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowPhotoActivity extends AppCompatActivity {

    @BindView(R.id.iv_photo_view)
    PhotoView ivPhotoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_photo);
        ButterKnife.bind(this);
        ImageLoader.loadImage(this,getIntent().getStringExtra("url"),ivPhotoView);
    }

    public static void actionStart(Context context, String url) {
        context.startActivity(new Intent(context, ShowPhotoActivity.class).putExtra("url", url));
    }
}
