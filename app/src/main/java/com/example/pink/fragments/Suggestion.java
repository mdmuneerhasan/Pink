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
import com.example.pink.ChatActivity;
import com.example.pink.R;
import com.example.pink.interface_package.Click;
import com.example.pink.utility.Connection;
import com.example.pink.firebase_classes.Item;
import com.example.pink.utility.RecyclerAdapter;
import com.example.pink.utility.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Suggestion extends Fragment implements Click {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_suggestion,container,false);
    }

    ArrayList<Item> list;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    ArrayList<String> group_id;
    DatabaseReference recentChat;
    DatabaseReference socket;
    Connection connection;
    UserInfo userInfo;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        connection=new Connection();
        recentChat=connection.getRecentChats();
        socket=connection.getSocketReference();
        userInfo=new UserInfo(getContext());
        group_id=new ArrayList<>();
        list=new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        recyclerAdapter=new RecyclerAdapter(list,"3",this);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        recentChat.child(userInfo.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                group_id.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    group_id.add(dataSnapshot1.getKey());
                }
                fetch();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void fetch() {
        list.clear();
        if (!list.isEmpty())
        for (String id: group_id) {
            socket.child(id).child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        Item item=snapshot.getValue(Item.class);
                        list.add(item);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    public void click(String uid) {
        Intent intent=new Intent(getContext(), ChatActivity.class);
        intent.putExtra("uid",uid);
        startActivity(intent);
    }
}
