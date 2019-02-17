package com.example.pink.firebase_classes;

public class Chat {
    String message,time,name,uid;

    public Chat() {
    }

    public Chat(String message, String time, String name, String uid) {
        this.message = message;
        this.time = time;
        this.name = name;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}