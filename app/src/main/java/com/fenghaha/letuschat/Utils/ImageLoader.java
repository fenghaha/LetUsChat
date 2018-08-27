package com.fenghaha.letuschat.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fenghaha.letuschat.R;

import java.io.File;

/**
 * Created by FengHaHa on2018/8/18 0018 2:06
 */
@SuppressLint("CheckResult")
public class ImageLoader {
   public static RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL);

    public static void loadImage(Context context, String url, ImageView target) {
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.drawable.ic_avatar)
//                .error(R.drawable.ic_avatar)
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(target);
    }

    public static void loadImage(Context context, File file, ImageView target) {
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.drawable.ic_avatar)
//                .error(R.drawable.ic_avatar)
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(file).apply(options).into(target);
    }

    public static void loadImage(Context context, int resID, ImageView target) {
//        RequestOptions options = new RequestOptions();
//        options.placeholder(R.drawable.ic_avatar)
//                .error(R.drawable.radius_drawable_bg)
//                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(resID).apply(options).into(target);
    }
}
