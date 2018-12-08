package com.itcinfotech.ssfpartner.pojo.user;

public class UpdateRegisterTokenRequest {

    String psid,key,deviceID;
    boolean isAndroid;

    public UpdateRegisterTokenRequest(String psid, String key, String deviceID, boolean isAndroid) {
        this.psid = psid;
        this.key = key;
        this.deviceID = deviceID;
        this.isAndroid = isAndroid;
    }

    public String getPsid() {
        return psid;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public boolean isAndroid() {
        return isAndroid;
    }

    public void setAndroid(boolean android) {
        isAndroid = android;
    }
}
