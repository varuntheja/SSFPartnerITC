package com.itcinfotech.ssfpartner.cabdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.adapters.CabTripsAdapter;
import com.itcinfotech.ssfpartner.network.SSFApiClient;
import com.itcinfotech.ssfpartner.network.SSFApiInterface;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResponse;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResult;
import com.itcinfotech.ssfpartner.pojo.food.FoodDashboardResponse;
import com.itcinfotech.ssfpartner.utility.RecyclerTouchListener;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpComingTripsFragment extends Fragment{

    private RecyclerView recyclerView;
    private CabTripsAdapter adapter;
    private TextView mNoOrders;
    private List<UpComingTripsResult> tripsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        mNoOrders = view.findViewById(R.id.tv_no_orders);
        mNoOrders.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new CabTripsAdapter(getActivity(), tripsList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(getActivity(), PassengersListActivity.class);
                intent.putExtra("ID", tripsList.get(position).getTripID());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        if (Utility.isNetworkAvailable(getActivity())) {
            getUpComingTripDetails(SessionManager.getInstance(getActivity()).getPSID());
        } else {
            Utility.showDialog(getActivity(), "Please check your internet connection");
        }
    }

    private void getUpComingTripDetails(String userId) {

        Utility.showProgresDialog(getActivity());
        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<UpComingTripsResponse> call = apiService.getUpComingTrips(SessionManager.getInstance(getActivity()).getSessionToken(), userId);
        call.enqueue(new Callback<UpComingTripsResponse>() {
            @Override
            public void onResponse(Call<UpComingTripsResponse> call, Response<UpComingTripsResponse> response) {

                Utility.dismessProgresDialog();

                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() != null && response.body().getIssuccess()) {

                            if(response.body().getResult()!=null && response.body().getResult().size()>0){

                                for(int i=0; i<response.body().getResult().size();i++){
                                    tripsList.add(response.body().getResult().get(i));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    if(tripsList.size() == 0){
                        mNoOrders.setVisibility(View.VISIBLE);
                    }
                    else {
                        mNoOrders.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Utility.showErrorAlert(getActivity());
                }
            }

            @Override
            public void onFailure(Call<UpComingTripsResponse> call, Throwable t) {
                Utility.dismessProgresDialog();
                Utility.printLog("response error " + t.getMessage());
            }
        });
    }

}
