package com.example.pink.utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {
    FirebaseDatabase database;
    DatabaseReference socket;
    DatabaseReference recentChats;
    DatabaseReference user;
    DatabaseReference message;

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

    public DatabaseReference getUser() {
        user=database.getReference("user");
        return user;
    }

    public DatabaseReference getMessage() {
        message=database.getReference("message");
        return message;
    }
}
