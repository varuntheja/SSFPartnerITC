
package com.itcinfotech.ssfpartner.pojo.food;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyUserResult {

    @SerializedName("PSID")
    @Expose
    private String pSID;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("Building")
    @Expose
    private String building;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("info")
    @Expose
    private List<Info> info = null;

    public String getPSID() {
        return pSID;
    }

    public void setPSID(String pSID) {
        this.pSID = pSID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Info> getInfo() {
        return info;
    }

    public void setInfo(List<Info> info) {
        this.info = info;
    }

}
