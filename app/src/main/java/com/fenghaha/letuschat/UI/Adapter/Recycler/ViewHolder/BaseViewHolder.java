package com.fenghaha.letuschat.UI.Adapter.Recycler.ViewHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.avos.avoscloud.AVUser;

import java.util.HashMap;

/**
 * Created by FengHaHa on2018/8/23 0023 14:46
 * ViewHolder基类 实现解耦
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder{
    public BaseViewHolder(Context context, ViewGroup root, int layoutRes) {
        super(LayoutInflater.from(context).inflate(layoutRes, root, false));
    }

    public Context getContext() {
        return itemView.getContext();
    }

    /**
     * 用给定的 data 对 holder 的 view 进行赋值
     */
    public abstract void bindData(T t );

}
