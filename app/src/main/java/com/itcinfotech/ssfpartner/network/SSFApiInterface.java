package com.itcinfotech.ssfpartner.network;

import com.itcinfotech.ssfpartner.pojo.cab.PassengersResponse;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResponse;
import com.itcinfotech.ssfpartner.pojo.food.FoodDashboardResponse;
import com.itcinfotech.ssfpartner.pojo.food.VerifyUserRequest;
import com.itcinfotech.ssfpartner.pojo.food.VerifyUserResponse;
import com.itcinfotech.ssfpartner.pojo.user.LoginRequest;
import com.itcinfotech.ssfpartner.pojo.user.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SSFApiInterface {

    @GET("FoodRequest/CountDetails")
    Call<FoodDashboardResponse> getFoodDashboardDetails(@Header("Authorization:bearer") String contentRange, @Query("date") String date);

    @POST("FoodRequest/Facilitate")
    Call<VerifyUserResponse> verifyUser(@Header("Authorization:bearer") String contentRange, @Body VerifyUserRequest body);

    @GET("Driver/Trips")
    Call<UpComingTripsResponse> getUpComingTrips(@Header("Authorization:bearer") String contentRange, @Query("ID") String id);

    @GET("Driver/TripDetails")
    Call<PassengersResponse> getPassengersList(@Header("Authorization:bearer") String contentRange, @Query("RequestID") String id);

    @POST("Driver")
    Call<LoginResponse> loginUser(@Header("Authorization:bearer") String contentRange, @Body LoginRequest body);

}
