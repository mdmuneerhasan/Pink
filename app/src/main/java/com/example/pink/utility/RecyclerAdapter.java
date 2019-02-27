package com.example.pink.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.pink.R;
import com.example.pink.firebase_classes.Item;
import com.example.pink.interface_package.Click;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Holder>{
    public  final String CHATS="1";
    public  final String PEOPLE="2";
    public  final String SUGGESTION="3";
    Context context;
    ArrayList<Item> list;
    String className;
    Click clicker;
    public RecyclerAdapter(ArrayList<Item> list, String className,Click click) {
        this.list = list;
        this.clicker=click;
        this.className = className;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.item_row_chats,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int i) {
        Item item=list.get(i);
        holder.tvName.setText(item.getName());
        holder.tvInfo.setText(item.getInfo());
       try{
           Picasso.get().load(item.getPhoto()).into(holder.imageView);
       }catch (Exception e){

       }
        if(className.equals(CHATS)){
            holder.tvTime.setText(item.getTime());
            if(item.getNumber()!=null){
                holder.tvNumber.setText(item.getNumber());
            }
            else{
                holder.tvNumber.setVisibility(View.GONE);
            }
            holder.btnConnect.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    click.click(list.get(holder.getAdapterPosition()).getUid());
                    clicker.click(list.get(holder.getAdapterPosition()).getUid());
                }
            });
        }
        if(className.equals(PEOPLE)){
            holder.tvTime.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.GONE);
            holder.btnConnect.setText("Disconnect");
        }

        if(className.equals(SUGGESTION)){
            holder.tvTime.setVisibility(View.GONE);
            holder.tvNumber.setVisibility(View.GONE);

            holder.btnConnect.setVisibility(View.GONE);
 //           holder.btnConnect.setText("Connect");
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class Holder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView tvName,tvInfo,tvTime,tvNumber;
        Button btnConnect;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageViewCircle);
            tvName=itemView.findViewById(R.id.tvName);
            tvInfo=itemView.findViewById(R.id.tvInfo);
            tvTime=itemView.findViewById(R.id.tvTime);
            tvNumber=itemView.findViewById(R.id.tvNumber);
            btnConnect=itemView.findViewById(R.id.btnConnect);
        }
    }

}
