package com.example.pink.utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {
    DatabaseReference socket;
    FirebaseDatabase database;
    DatabaseReference recentChats;

    public Connection() {
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getSocketReference() {
        socket=database.getReference("socket");
        return socket;
    }

    public DatabaseReference getRecentChats() {
        recentChats=database.getReference("recentChats");
        return recentChats;
    }
}
