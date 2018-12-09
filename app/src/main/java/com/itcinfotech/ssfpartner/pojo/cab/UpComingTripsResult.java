
package com.itcinfotech.ssfpartner.pojo.cab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpComingTripsResult {

    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("FROM")
    @Expose
    private String fROM;
    @SerializedName("TO")
    @Expose
    private String tO;
    @SerializedName("TripID")
    @Expose
    private String tripID;
    @SerializedName("DATE")
    @Expose
    private String dATE;

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getFROM() {
        return fROM;
    }

    public void setFROM(String fROM) {
        this.fROM = fROM;
    }

    public String getTO() {
        return tO;
    }

    public void setTO(String tO) {
        this.tO = tO;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDATE() {
        return dATE;
    }

    public void setDATE(String dATE) {
        this.dATE = dATE;
    }

}
