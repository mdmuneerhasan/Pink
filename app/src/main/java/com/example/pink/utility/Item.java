package com.example.pink.utility;

public class Item {
    String photo,name,info,time,number;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Item() {
    }

    public Item(String photo, String name, String info, String time, String number) {
        this.photo = photo;
        this.name = name;
        this.info = info;
        this.time = time;
        this.number = number;
    }
}
