package com.example.kundan6singh.testproject.Filter.MessageActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kundan6singh.testproject.Filter.FilterActivity;
import com.example.kundan6singh.testproject.Filter.FilterListAdapter;
import com.example.kundan6singh.testproject.Filter.FilterListModel;
import com.example.kundan6singh.testproject.R;

import java.util.ArrayList;

/**
 * Created by Kundan6.Singh on 20-12-2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    ArrayList<MessageModel> alMessageModel;
    Context ctx;

    public MessageAdapter(Context context, ArrayList<MessageModel> alMessageModel) {
        this.ctx = context;
        this.alMessageModel = alMessageModel;
    }

    @Override
    public MessageAdapter.MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, null);
        MessageHolder myViewHolderObj = new MessageHolder(layoutView);
        return myViewHolderObj;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MessageHolder holder, int position) {
        final MessageModel messageModel = alMessageModel.get(position);
        holder.tvMsgTitle.setText(messageModel.getMessageTitle());
        holder.tvMsgDate.setText(messageModel.getMessageDate());
        holder.tvMsgDesc.setText(messageModel.getMessageDesc());
        String messageType = messageModel.getMessageLinkType();
        //1.PLAYSTORE,2.NONE,3.WEBPAGE,4.ACTIVITY
        if (messageType.trim().equalsIgnoreCase("1")) {
            holder.tvAction.setVisibility(View.VISIBLE);
            holder.tvAction.setText(messageModel.getMessageButtonName());
        } else if (messageType.trim().equalsIgnoreCase("2")) {
            holder.tvAction.setVisibility(View.GONE);
        } else if (messageType.trim().equalsIgnoreCase("3")) {
            holder.tvAction.setVisibility(View.VISIBLE);
            holder.tvAction.setText(messageModel.getMessageButtonName());
        } else if (messageType.trim().equalsIgnoreCase("4")) {
            holder.tvAction.setVisibility(View.VISIBLE);
            holder.tvAction.setText(messageModel.getMessageButtonName());
        }
    }

    @Override
    public int getItemCount() {
        return alMessageModel.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView tvMsgTitle, tvMsgDate, tvMsgDesc, tvAction;
        Button btnAction;

        public MessageHolder(View itemView) {
            super(itemView);
            tvMsgTitle = (TextView) itemView.findViewById(R.id.tvMsgTitle);
            tvMsgDate = (TextView) itemView.findViewById(R.id.tvMsgDate);
            tvMsgDesc = (TextView) itemView.findViewById(R.id.tvMsgDesc);
            tvAction = (TextView) itemView.findViewById(R.id.tvAction);
        }
    }
}
