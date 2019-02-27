package com.example.pink.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.pink.GroupChatActivity;
import com.example.pink.R;
import com.example.pink.interface_package.Click;
import com.example.pink.utility.Connection;
import com.example.pink.firebase_classes.Item;
import com.example.pink.utility.RecyclerAdapter;
import com.example.pink.utility.UserInfo;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class Chats extends Fragment implements Click {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats,container,false);
    }
    String name;
    String userid;
    ArrayList<Item> list;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Connection connection;
    DatabaseReference databaseReferenceRecent;
    DatabaseReference databaseReferenceSocket;
    UserInfo userInfo;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        userInfo =new UserInfo(getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerAdapter=new RecyclerAdapter(list,"1",this);
        recyclerView.setAdapter(recyclerAdapter);

        connection=new Connection();

        databaseReferenceRecent=connection.getRecentChats();
        databaseReferenceSocket=connection.getSocketReference();

        recyclerAdapter.notifyDataSetChanged();





    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReferenceRecent.child(userInfo.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Item item=dataSnapshot1.getValue(Item.class);
                    list.add(item);
                }
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void click(String uid) {
        Intent intent=new Intent(getContext(), GroupChatActivity.class);
        intent.putExtra("uid",uid);
        getActivity().startActivity(intent);
    }
}
