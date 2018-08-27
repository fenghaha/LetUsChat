package com.fenghaha.letuschat.UI.Adapter.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVStatus;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.Utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FengHaHa on2018/8/27 0027 21:52
 */
public class StatusRecAdapter extends RecyclerView.Adapter<StatusRecAdapter.ViewHolder> {


    private Context context;


    private List<AVStatus> mStatusList;

    public StatusRecAdapter(Context context) {
        this.context = context;
        mStatusList = new ArrayList<>();
    }

    public void setStatusList(List<AVStatus> list) {
        mStatusList.clear();
        mStatusList.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AVStatus status = mStatusList.get(position);
        holder.authorName.setText((String) status.get("name"));
        holder.content.setText((String) status.get("text"));
        ImageLoader.loadImage(context, (String) status.get("image"), holder.image);
        ImageLoader.loadImage(context, (String) status.get("avatar"), holder.avatar);
    }


    @Override
    public int getItemCount() {
        return mStatusList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView authorName;
        TextView content;
        CircleImageView avatar;
        ImageView image;
        TextView date;


        ViewHolder(View itemView) {
            super(itemView);
            authorName = itemView.findViewById(R.id.tv_author_name);
            content = itemView.findViewById(R.id.tv_content);
            date = itemView.findViewById(R.id.tv_date);
            image = itemView.findViewById(R.id.iv_image);
            avatar = itemView.findViewById(R.id.image_avatar);
        }
    }

}
