package com.mountreachsolution.campusflow;

public class POJOnotice {
    String id,date,time,title,dis,image;

    public POJOnotice(String id, String date, String time, String title, String dis, String image) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.dis = dis;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDis() {
        return dis;
    }

    public void setDis(String dis) {
        this.dis = dis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
