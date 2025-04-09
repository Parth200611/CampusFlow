package com.hyperX.campusflow;

public class POJOwork {
    String selectedStatus, selectedGender, fullname, adharNo, mobileNo, address, dob, joiningDate, qualification,id;

    public POJOwork(String selectedStatus, String selectedGender, String fullname, String adharNo, String mobileNo, String address, String dob, String joiningDate, String qualification,String id) {
        this.selectedStatus = selectedStatus;
        this.selectedGender = selectedGender;
        this.fullname = fullname;
        this.adharNo = adharNo;
        this.mobileNo = mobileNo;
        this.address = address;
        this.dob = dob;
        this.joiningDate = joiningDate;
        this.qualification = qualification;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    public String getSelectedGender() {
        return selectedGender;
    }

    public void setSelectedGender(String selectedGender) {
        this.selectedGender = selectedGender;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAdharNo() {
        return adharNo;
    }

    public void setAdharNo(String adharNo) {
        this.adharNo = adharNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
