
package com.itcinfotech.ssfpartner.pojo.food;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodDashboardResponse {

    @SerializedName("Issuccess")
    @Expose
    private Boolean issuccess;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("ApiError")
    @Expose
    private Object apiError;
    @SerializedName("Result")
    @Expose
    private List<Result> result = null;

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

    public Object getApiError() {
        return apiError;
    }

    public void setApiError(Object apiError) {
        this.apiError = apiError;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

}
