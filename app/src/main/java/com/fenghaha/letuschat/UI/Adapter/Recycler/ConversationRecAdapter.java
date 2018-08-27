package com.fenghaha.letuschat.UI.Adapter.Recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.fenghaha.letuschat.MVP.Contract.BaseContract;
import com.fenghaha.letuschat.R;
import com.fenghaha.letuschat.UI.Activity.ChatActivity;
import com.fenghaha.letuschat.Utils.ChatUtil;
import com.fenghaha.letuschat.Utils.DateUtil;
import com.fenghaha.letuschat.Utils.ImageLoader;
import com.fenghaha.letuschat.Utils.MyApp;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by FengHaHa on2018/8/25 0025 22:56
 */
public class ConversationRecAdapter extends RecyclerView.Adapter<ConversationRecAdapter.ViewHolder> {
    private Context context;
    private List<AVIMConversation> mConversations;
    private List<AVUser> mUserList;

    public ConversationRecAdapter(Context context) {
        this.context = context;
        mConversations = new ArrayList<>();
        mUserList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contact_or_message_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if (!holder.wrapperView.hasOnClickListeners()) {
            holder.initListener(mUserList.get(i).getObjectId());
        }
        AVIMConversation conversation = mConversations.get(i);
        AVUser user = mUserList.get(i);
        ImageLoader.loadImage(context, (String) user.get("avatarUrl"), holder.mAvatar);
        holder.tvStatus.setText((String) conversation.get("lastMessage"));
        holder.tvName.setText((String) user.get("nickname"));
        holder.time.setVisibility(View.VISIBLE);
        holder.time.setText(DateUtil.DateToString(conversation.getLastMessageAt()));
    }

    private void initData() {
        mUserList.clear();
        for (AVIMConversation c : mConversations) {
            AVUser user = null;
            if (c.getCreator().equals(AVUser.getCurrentUser().getObjectId())) {
                for (String s : c.getMembers()) {
                    if (MyApp.getAvUserHashMap().containsKey(s)) {
                        user = MyApp.getAvUserHashMap().get(s);
                        break;
                    }
                }
            } else {
                user = MyApp.getAvUserHashMap().get(c.getCreator());
            }
            mUserList.add(user);
        }
    }

    public void showMessage(AVIMMessage message) {
        for (int i = 0; i < mConversations.size(); i++) {
            AVIMConversation c = mConversations.get(i);
            if (message.getConversationId().equals(c.getConversationId())) {
                AVIMTextMessage tm = (AVIMTextMessage) message;
                c.set("lastMessage", tm.getText());
                notifyItemChanged(i);
                return;
            }
        }
        ChatUtil.getConversation(message.getConversationId(), new BaseContract.BaseCallBack<AVIMConversation>() {
            @Override
            public void onSuccess(AVIMConversation data) {
                mConversations.add(0, data);
                initData();
                notifyDataSetChanged();
            }
        });

    }


//    public void showMessage(AVIMMessage message) {
//        for (int i = 0; i < mConversations.size(); i++) {
//            AVIMConversation c = mConversations.get(i);
//            if (message.getConversationId().equals(c.getConversationId())) {
//                AVIMConversationsQuery query = MyApp.getCurrentClient().getConversationsQuery();
//                query.setWithLastMessagesRefreshed(true);
//                query.whereEqualTo("objectId",c.getConversationId());
//                int finalI = i;
//                query.findInBackground(new AVIMConversationQueryCallback(){
//                    @Override
//                    public void done(List<AVIMConversation> convs,AVIMException e){
//                        if(e==null){
//                            if(convs!=null && !convs.isEmpty()){
//                              mConversations.set(finalI,convs.get(0));
//                              notifyItemChanged(finalI);
//                                //convs.get(0) 就是想要的conversation
//                            }
//                        }
//                    }
//                });
//                return;
//            }
//        }
//        ChatUtil.getConversation(message.getConversationId(), new BaseContract.BaseCallBack<AVIMConversation>() {
//            @Override
//            public void onSuccess(AVIMConversation data) {
//                mConversations.add(0,data);
//                initData();
//                notifyDataSetChanged();
//            }
//        });
//    }

    public void addConversationList(List<AVIMConversation> list) {
        mConversations.addAll(list);
        initData();
        notifyDataSetChanged();
    }

    public void setConversationList(List<AVIMConversation> list) {
        mConversations.clear();
        mUserList.clear();
        mConversations.addAll(list);
        initData();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mConversations.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mAvatar;
        TextView tvName;
        TextView tvStatus;
        TextView time;
        View wrapperView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            wrapperView = itemView;
            mAvatar = itemView.findViewById(R.id.iv_avatar);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_status);
            time = itemView.findViewById(R.id.tv_time);
        }

        void initListener(String id) {
            wrapperView.setOnClickListener(v -> ChatActivity.actionStart(context, id));
        }
    }

}
