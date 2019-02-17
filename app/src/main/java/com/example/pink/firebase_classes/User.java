package com.example.pink.firebase_classes;

public class User {
    String name,email,photo,uid,number,about;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public User() {
    }

    public User(String name, String email, String photo, String uid, String number, String about) {
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.uid = uid;
        this.number = number;
        this.about = about;
    }
}
