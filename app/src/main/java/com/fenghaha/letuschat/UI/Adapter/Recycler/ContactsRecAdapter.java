package com.fenghaha.letuschat.UI.Adapter.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Activity.ChatActivity;
import com.fenghaha.letuschat.Utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FengHaHa on2018/8/24 0024 18:03
 */
public class ContactsRecAdapter extends RecyclerView.Adapter<ContactsRecAdapter.ViewHolder> {

    private Context context;
    private List<AVUser> mContacts;

    public ContactsRecAdapter(Context context) {
        this.context = context;
        mContacts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contact_or_message_layout, viewGroup, false);
        ViewHolder holder =  new ViewHolder(view);
        holder.initListener(mContacts.get(i));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        AVUser user = mContacts.get(i);
        ImageLoader.loadImage(context, (String) user.get("avatarUrl"), holder.mAvatar);
        holder.tvStatus.setText((String)user.get("motto"));
        holder.tvName.setText((String)user.get("nickname"));

    }

    public void addContactList(List<AVUser> list) {
        mContacts.clear();
        mContacts.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mAvatar;
        TextView tvName;
        TextView tvStatus;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }

        void initListener(AVUser avUser) {
            itemView.setOnClickListener(v -> ChatActivity.actionStart(context, avUser.getObjectId()));
        }
    }

}
