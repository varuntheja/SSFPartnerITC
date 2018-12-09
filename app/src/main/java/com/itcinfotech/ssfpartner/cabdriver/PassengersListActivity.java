package com.itcinfotech.ssfpartner.cabdriver;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.adapters.CabTripsAdapter;
import com.itcinfotech.ssfpartner.adapters.PassengersAdapter;
import com.itcinfotech.ssfpartner.network.SSFApiClient;
import com.itcinfotech.ssfpartner.network.SSFApiInterface;
import com.itcinfotech.ssfpartner.pojo.cab.PassengersResponse;
import com.itcinfotech.ssfpartner.pojo.cab.PassengersResult;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResponse;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResult;
import com.itcinfotech.ssfpartner.utility.RecyclerTouchListener;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengersListActivity extends AppCompatActivity {

    private PassengersAdapter adapter;
    private TextView mNoOrders;
    private List<PassengersResult> passengersList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passengers_list);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Passengers List");
        Utility.applyFontForToolbarTitle(this, mToolbar);

        mNoOrders = findViewById(R.id.no_passengers);
        RecyclerView recyclerView = findViewById(R.id.passengers_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new PassengersAdapter(this, passengersList);
        recyclerView.setAdapter(adapter);

        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(PassengersListActivity.this, MapLocationsActivity.class);
                intent.putExtra("LatLng", passengersList.get(position).getTolatLong());
                startActivity(intent);
                overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        if (Utility.isNetworkAvailable(PassengersListActivity.this)) {
            getPassengersDetails(getIntent().getStringExtra("ID"));
        } else {
            Utility.showDialog(PassengersListActivity.this, "Please check your internet connection");
        }
    }

    private void getPassengersDetails(String requestId) {

        Utility.showProgresDialog(PassengersListActivity.this);
        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<PassengersResponse> call = apiService.getPassengersList(SessionManager.getInstance(PassengersListActivity.this).getSessionToken(), requestId);
        call.enqueue(new Callback<PassengersResponse>() {
            @Override
            public void onResponse(Call<PassengersResponse> call, Response<PassengersResponse> response) {

                Utility.dismessProgresDialog();

                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() != null && response.body().getIssuccess()) {

                            if(response.body().getResult()!=null && response.body().getResult().size()>0){

                                for(int i=0; i<response.body().getResult().size();i++){
                                    passengersList.add(response.body().getResult().get(i));
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    if(passengersList.size() == 0){
                        mNoOrders.setVisibility(View.VISIBLE);
                    }
                    else {
                        mNoOrders.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Utility.showErrorAlert(PassengersListActivity.this);
                }
            }

            @Override
            public void onFailure(Call<PassengersResponse> call, Throwable t) {
                Utility.dismessProgresDialog();
                Utility.printLog("response error " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.anim_three, R.anim.anim_four);
    }
}
