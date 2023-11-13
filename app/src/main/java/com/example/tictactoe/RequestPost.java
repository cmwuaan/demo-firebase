package com.example.tictactoe;

public class RequestPost {
    String email, fullName, phoneNum, password;

    public RequestPost(String email, String fullName, String phoneNum, String password) {
        this.email = email;
        this.fullName = fullName;
        this.phoneNum = phoneNum;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
