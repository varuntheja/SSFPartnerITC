
package com.itcinfotech.ssfpartner.pojo.cab;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpComingTripsResponse {

    @SerializedName("Issuccess")
    @Expose
    private Boolean issuccess;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ApiError")
    @Expose
    private String apiError;
    @SerializedName("Result")
    @Expose
    private List<UpComingTripsResult> result = null;

    public Boolean getIssuccess() {
        return issuccess;
    }

    public void setIssuccess(Boolean issuccess) {
        this.issuccess = issuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApiError() {
        return apiError;
    }

    public void setApiError(String apiError) {
        this.apiError = apiError;
    }

    public List<UpComingTripsResult> getResult() {
        return result;
    }

    public void setResult(List<UpComingTripsResult> result) {
        this.result = result;
    }

}
