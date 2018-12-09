
package com.itcinfotech.ssfpartner.pojo.food;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("TotalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("Remaining")
    @Expose
    private Integer remaining;
    @SerializedName("Serverd")
    @Expose
    private Integer serverd;
    @SerializedName("foodTiming")
    @Expose
    private Integer foodTiming;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getServerd() {
        return serverd;
    }

    public void setServerd(Integer serverd) {
        this.serverd = serverd;
    }

    public Integer getFoodTiming() {
        return foodTiming;
    }

    public void setFoodTiming(Integer foodTiming) {
        this.foodTiming = foodTiming;
    }

}
