package com.itcinfotech.ssfpartner.network;

import com.itcinfotech.ssfpartner.pojo.food.FoodDashboardResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface SSFApiInterface {

    /*@GET("user/getinfo")
    Call<User> getUser(@Header("Authorization:bearer") String contentRange, @Query("psid") String psid);

    @POST("push")
    Call<ApprovalsHelper> updateToken(@Header("Authorization:bearer") String contentRange, @Body UpdateRegisterTokenRequest request);*/

    @GET("FoodRequest/CountDetails")
    Call<FoodDashboardResponse> getFoodDashboardDetails(@Header("Authorization:bearer") String contentRange, @Query("date") String date);
}
