
package com.itcinfotech.ssfpartner.pojo.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResult {

    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("IsDriver")
    @Expose
    private boolean isDriver;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public boolean getIsDriver() {
        return isDriver;
    }

    public void setIsDriver(boolean isDriver) {
        this.isDriver = isDriver;
    }

}
