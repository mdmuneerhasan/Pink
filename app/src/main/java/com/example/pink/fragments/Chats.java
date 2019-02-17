package com.example.pink.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.pink.R;
import com.example.pink.utility.Connection;
import com.example.pink.utility.Item;
import com.example.pink.utility.RecyclerAdapter;
import com.example.pink.utility.UserInfo;
import com.google.firebase.database.*;

import java.util.ArrayList;

public class Chats extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chats,container,false);
    }

    ArrayList<Item> list;
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    Connection connection;
    DatabaseReference databaseReferenceRecent;
    DatabaseReference databaseReferenceuser;
    UserInfo userInfo;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        userInfo =new UserInfo(getContext());


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        connection=new Connection();
        databaseReferenceRecent=connection.getRecentChats();
        databaseReferenceuser=connection.getUser();
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2",""));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2",""));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2",""));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2",""));


        recyclerAdapter=new RecyclerAdapter(list,"1");
        recyclerView.setAdapter(recyclerAdapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        databaseReferenceRecent.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.child(userInfo.getUid()).getChildren()) {
                    String userid=dataSnapshot1.getKey();



                    String name=dataSnapshot1.getValue(String.class);
                    String photo,info,time,number;
                    list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg",name,"info","12:30","2",userid));
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
