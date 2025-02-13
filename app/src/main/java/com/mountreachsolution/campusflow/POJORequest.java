package com.mountreachsolution.campusflow;

public class POJORequest {
    String id,name,branch,sem,enroll,strt,end,title,dis,image;

    public POJORequest(String id, String name, String branch, String sem, String enroll, String strt, String end, String title, String dis) {
        this.id = id;
        this.name = name;
        this.branch = branch;
        this.sem = sem;
        this.enroll = enroll;
        this.strt = strt;
        this.end = end;
        this.title = title;
        this.dis = dis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getEnroll() {
        return enroll;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public String getStrt() {
        return strt;
    }

    public void setStrt(String strt) {
        this.strt = strt;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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


}
