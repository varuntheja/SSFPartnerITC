package com.itcinfotech.ssfpartner.food;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.network.SSFApiClient;
import com.itcinfotech.ssfpartner.network.SSFApiInterface;
import com.itcinfotech.ssfpartner.pojo.food.FoodDashboardResponse;
import com.itcinfotech.ssfpartner.utility.Constants;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodDashboardFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mBreakfastLayout, mLunchLayout, mDinnerLayout;
    private TextView mBreakfastCount, mBreakfastServed, mLunchCount, mLunchServed, mDinnerCount, mDinnerServed, mSelectedDate;
    private String dashboardDate;
    private final int REQUEST_VERIFY = 12;

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
            getFoodDashboardDetails();
        } else {
            Utility.showDialog(getActivity(), "Please check your internet connection");
        }
    }

    private void initViews(View view) {

        mSelectedDate = view.findViewById(R.id.tv_date);
        mBreakfastLayout = view.findViewById(R.id.layout_breakfast);
        mLunchLayout = view.findViewById(R.id.layout_lunch);
        mDinnerLayout = view.findViewById(R.id.layout_dinner);

        mBreakfastCount = view.findViewById(R.id.tv_breakfast_to_serve);
        mBreakfastServed = view.findViewById(R.id.tv_breakfast_served);
        mLunchCount = view.findViewById(R.id.tv_lunch_to_serve);
        mLunchServed = view.findViewById(R.id.tv_lunch_served);
        mDinnerCount = view.findViewById(R.id.tv_dinner_to_serve);
        mDinnerServed = view.findViewById(R.id.tv_dinner_served);
        view.findViewById(R.id.layout_selected_date).setOnClickListener(this);

        mBreakfastLayout.setOnClickListener(this);
        mLunchLayout.setOnClickListener(this);
        mDinnerLayout.setOnClickListener(this);

        dashboardDate = Utility.getCurrentDate("yyyy-MM-dd");
        mSelectedDate.setText(Utility.changeDateFormat(dashboardDate, "yyyy-MM-dd", "dd-MMM-yyyy"));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layout_breakfast:

                Intent bfIntent = new Intent(getActivity(), VerifyDetailsActivity.class);
                bfIntent.putExtra(Constants.EXTRA_TYPE, Constants.EXTRA_BREAKFAST);
                startActivityForResult(bfIntent, REQUEST_VERIFY);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
            case R.id.layout_lunch:

                Intent lunchIntent = new Intent(getActivity(), VerifyDetailsActivity.class);
                lunchIntent.putExtra(Constants.EXTRA_TYPE, Constants.EXTRA_LUNCH);
                startActivityForResult(lunchIntent, REQUEST_VERIFY);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);

                break;

            case R.id.layout_dinner:

                Intent dinerIntent = new Intent(getActivity(), VerifyDetailsActivity.class);
                dinerIntent.putExtra(Constants.EXTRA_TYPE, Constants.EXTRA_DINNER);
                startActivityForResult(dinerIntent, REQUEST_VERIFY);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
            case R.id.layout_selected_date:

                showDatePickerDialog();
                break;
        }
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                dashboardDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                mSelectedDate.setText(Utility.changeDateFormat(dashboardDate, "yyyy-MM-dd", "dd-MMM-yyyy"));
                getFoodDashboardDetails();
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void getFoodDashboardDetails() {

        Utility.showProgresDialog(getActivity());
        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<FoodDashboardResponse> call = apiService.getFoodDashboardDetails(SessionManager.getInstance(getActivity()).getSessionToken(), dashboardDate);
        call.enqueue(new Callback<FoodDashboardResponse>() {
            @Override
            public void onResponse(Call<FoodDashboardResponse> call, Response<FoodDashboardResponse> response) {

                Utility.dismessProgresDialog();
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<FoodDashboardResponse> call, Throwable t) {
                Utility.dismessProgresDialog();
                Utility.printLog("response error " + t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_VERIFY){

            if (Utility.isNetworkAvailable(getActivity())) {
                getFoodDashboardDetails();
            } else {
                Utility.showDialog(getActivity(), "Please check your internet connection");
            }
        }
    }
}
