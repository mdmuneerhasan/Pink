package com.example.pink.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class UserInfo {
    String name,email,photo,uid,number;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public  final String NULL_VALUES="";

    public UserInfo( Context context) {
        this.context = context;
        sharedPreferences =context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        this.name = sharedPreferences.getString("name","name");
        this.email = sharedPreferences.getString("email","email");
        this.photo = sharedPreferences.getString("photo","https://english.cdn.zeenews.com/sites/default/files/2016/12/23/557234-srk.jpg");
        this.uid = sharedPreferences.getString("uid",NULL_VALUES);
        this.number =sharedPreferences.getString("number","phone number");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 0) {
            editor.putString("name", name);
            editor.apply();
            this.name = name;
        }
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.length() > 0) {
            editor.putString("email", email);
            editor.apply();
            this.email = email;
        }
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        if (photo.length() > 0) {
            editor.putString("photo", photo);
            editor.apply();
            this.photo = photo;
        }
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        editor.putString("uid",uid);
        editor.apply();
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        if (number.length() > 0) {
            editor.putString("number", number);
            editor.apply();
            this.number = number;
        }
    }
}
