package com.hyperX.campusflow;

public class POJOALLStudent {
    String id,name,barnch,sem,enroll,roomno;

    public POJOALLStudent(String id, String name, String barnch, String sem, String enroll, String roomno) {
        this.id = id;
        this.name = name;
        this.barnch = barnch;
        this.sem = sem;
        this.enroll = enroll;
        this.roomno = roomno;
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

    public String getBarnch() {
        return barnch;
    }

    public void setBarnch(String barnch) {
        this.barnch = barnch;
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

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }
}
