package com.example.miniprogram;

public class Student {
    private String fullName;
    private String mobileNumber;

    private String email;

    public Student(String fullName, String mobileNumber, String email) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }


}

