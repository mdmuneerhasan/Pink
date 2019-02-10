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
import com.example.pink.utility.Item;
import com.example.pink.utility.RecyclerAdapter;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);



        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));
        list.add(new Item("https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg","muneer","info","12:30","2"));

        recyclerAdapter=new RecyclerAdapter(list,"1");
        recyclerView.setAdapter(recyclerAdapter);


    }
}
