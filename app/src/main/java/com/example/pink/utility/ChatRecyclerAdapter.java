package com.example.pink.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.pink.R;
import com.example.pink.firebase_classes.Chat;

import java.util.ArrayList;

public class ChatRecyclerAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    ArrayList<Chat> chatArrayList;
    UserInfo userInfo;
    public static final int ID_SENT=1;
    public static final int ID_RECEIVED=2;

    public ChatRecyclerAdapter(ArrayList<Chat> chatArrayList,Context context) {
        this.chatArrayList = chatArrayList;
        this.context=context;
        userInfo=new UserInfo(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if(i==ID_SENT){
            View view=layoutInflater.inflate(R.layout.item_row_send,viewGroup,false);
            return new Sended(view);
        }else if(i==ID_RECEIVED){
            View view=layoutInflater.inflate(R.layout.item_row_recieve,viewGroup,false);
            return new Received(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        String uid=chatArrayList.get(position).getUid();
        if(userInfo.getUid().equals(uid)){
            return ID_RECEIVED;
        }
        else {
            return ID_SENT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemId=getItemViewType(i);
        Chat chat=chatArrayList.get(i);
        if (itemId==ID_RECEIVED){
            Received received= (Received) viewHolder;
            received.tvMessage.setText(chat.getMessage());
            received.tvName.setText("you");
            received.tvTime.setText(chat.getTime());

        }else if(itemId==ID_SENT){
            Sended sended= (Sended) viewHolder;
            sended.tvTime.setText(chat.getTime());
            sended.tvName.setText(chat.getName());
            sended.tvMessage.setText(chat.getMessage());
        }


    }

    @Override
    public int getItemCount() {
        return chatArrayList.size();
    }

    class Sended extends RecyclerView.ViewHolder{
        TextView tvName,tvTime,tvMessage;
        public Sended(@NonNull View itemView) {
            super(itemView);
            tvMessage=itemView.findViewById(R.id.tvMessage);
            tvName=itemView.findViewById(R.id.tvSenderName);
            tvTime=itemView.findViewById(R.id.tvTime);

        }
    }
    class Received extends RecyclerView.ViewHolder{
        TextView tvName,tvTime,tvMessage;
        public Received(@NonNull View itemView) {
            super(itemView);
            tvMessage=itemView.findViewById(R.id.tvMessage);
            tvName=itemView.findViewById(R.id.tvSenderName);
            tvTime=itemView.findViewById(R.id.tvTime);
        }
    }
}
