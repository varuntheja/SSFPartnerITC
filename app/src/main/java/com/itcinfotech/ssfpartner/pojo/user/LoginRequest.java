package com.itcinfotech.ssfpartner.pojo.user;

public class LoginRequest {

    String PhoneNo, password;

    public String getPhoneNumber() {
        return PhoneNo;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNo = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
