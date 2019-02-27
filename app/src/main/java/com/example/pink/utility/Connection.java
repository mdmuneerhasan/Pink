package com.example.pink.utility;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Connection {
    FirebaseDatabase database;
    DatabaseReference socket;
    DatabaseReference recentChats;
    DatabaseReference newMessage;
    DatabaseReference user;
    DatabaseReference message;

    public Connection() {
        database = FirebaseDatabase.getInstance();
    }

    public DatabaseReference getSocketReference() {
        socket=database.getReference("socket");
        socket.keepSynced(true);
        return socket;
    }

    public DatabaseReference getRecentChats() {
        recentChats=database.getReference("recentChats");
        recentChats.keepSynced(true);
        return recentChats;

    }

    public DatabaseReference getNewMessage() {
        newMessage=database.getReference("newMessage");
        newMessage.keepSynced(true);
        return newMessage;
    }

    public DatabaseReference getUser() {
        user=database.getReference("user");
        user.keepSynced(true);
        return user;
    }

    public DatabaseReference getMessage() {
        message=database.getReference("message");
        message.keepSynced(true);
        return message;
    }

}
