package com.itcinfotech.ssfpartner.pojo.food;

public class VerifyUserRequest {

    String dateTime;
    int psid, foodTiming;

    public VerifyUserRequest(String dateTime, int psid, int foodTiming) {
        this.dateTime = dateTime;
        this.psid = psid;
        this.foodTiming = foodTiming;
    }

    public VerifyUserRequest() {
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getPsid() {
        return psid;
    }

    public void setPsid(int psid) {
        this.psid = psid;
    }

    public int getFoodTiming() {
        return foodTiming;
    }

    public void setFoodTiming(int foodTiming) {
        this.foodTiming = foodTiming;
    }
}
