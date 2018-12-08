package com.itcinfotech.ssfpartner.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.network.SSFApiClient;
import com.itcinfotech.ssfpartner.network.SSFApiInterface;
import com.itcinfotech.ssfpartner.pojo.food.FoodDashboardResponse;
import com.itcinfotech.ssfpartner.utility.Constants;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDashboardFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mBreakfastLayout, mLunchLayout, mDinnerLayout;
    private TextView mBreakfastCount, mBreakfastServed, mLunchCount, mLunchServed, mDinnerCount, mDinnerServed;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_dashboard, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        if (Utility.isNetworkAvailable(getActivity())) {

            getFoodDashboardDetails(Utility.getCurrentDate("yyyy-MM-dd"));
        } else {
            Utility.showDialog(getActivity(), "Please check your internet connection");
        }
    }

    private void initViews(View view) {

        mBreakfastLayout = view.findViewById(R.id.layout_breakfast);
        mLunchLayout = view.findViewById(R.id.layout_lunch);
        mDinnerLayout = view.findViewById(R.id.layout_dinner);

        mBreakfastCount = view.findViewById(R.id.tv_breakfast_to_serve);
        mBreakfastServed = view.findViewById(R.id.tv_breakfast_served);
        mLunchCount = view.findViewById(R.id.tv_lunch_to_serve);
        mLunchServed = view.findViewById(R.id.tv_lunch_served);
        mDinnerCount = view.findViewById(R.id.tv_dinner_to_serve);
        mDinnerServed = view.findViewById(R.id.tv_dinner_served);

        mBreakfastLayout.setOnClickListener(this);
        mLunchLayout.setOnClickListener(this);
        mDinnerLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layout_breakfast:

                Intent bfIntent = new Intent(getActivity(), VerifyDetailsActivity.class);
                bfIntent.putExtra(Constants.EXTRA_TYPE, Constants.EXTRA_BREAKFAST);
                startActivity(bfIntent);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
            case R.id.layout_lunch:

                Intent lunchIntent = new Intent(getActivity(), VerifyDetailsActivity.class);
                lunchIntent.putExtra(Constants.EXTRA_TYPE, Constants.EXTRA_LUNCH);
                startActivity(lunchIntent);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);

                break;

            case R.id.layout_dinner:

                Intent dinerIntent = new Intent(getActivity(), VerifyDetailsActivity.class);
                dinerIntent.putExtra(Constants.EXTRA_TYPE, Constants.EXTRA_DINNER);
                startActivity(dinerIntent);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
        }
    }

    private void getFoodDashboardDetails(String date) {

        Utility.showProgresDialog(getActivity());
        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<FoodDashboardResponse> call = apiService.getFoodDashboardDetails(SessionManager.getInstance(getActivity()).getSessionToken(), date);
        call.enqueue(new Callback<FoodDashboardResponse>() {
            @Override
            public void onResponse(Call<FoodDashboardResponse> call, Response<FoodDashboardResponse> response) {

                Utility.dismessProgresDialog();
                if (response != null && response.isSuccessful()) {
                    if (response.body() != null) {

                        for (int i = 0; i < response.body().getResult().size(); i++) {

                            if (response.body().getResult().get(i).getFoodTiming() == 1) {

                                mBreakfastCount.setText("To Serve : " + response.body().getResult().get(i).getRemaining());
                                mBreakfastServed.setText("Total Served : " + response.body().getResult().get(i).getServerd());

                            } else if (response.body().getResult().get(i).getFoodTiming() == 2) {

                                mLunchCount.setText("To Serve : " + response.body().getResult().get(i).getRemaining());
                                mLunchServed.setText("Total Served : " + response.body().getResult().get(i).getServerd());
                            } else if (response.body().getResult().get(i).getFoodTiming() == 3) {

                                mDinnerCount.setText("To Serve : " + response.body().getResult().get(i).getRemaining());
                                mDinnerServed.setText("Total Served : " + response.body().getResult().get(i).getServerd());
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FoodDashboardResponse> call, Throwable t) {
                Utility.dismessProgresDialog();
                Utility.printLog("response error " + t.getMessage());
            }
        });
    }
}
