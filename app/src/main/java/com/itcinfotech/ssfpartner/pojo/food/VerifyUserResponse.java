
package com.itcinfotech.ssfpartner.pojo.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyUserResponse {

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
    private VerifyUserResult result;

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

    public VerifyUserResult getResult() {
        return result;
    }

    public void setResult(VerifyUserResult result) {
        this.result = result;
    }

}
