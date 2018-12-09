package com.itcinfotech.ssfpartner.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.cabdriver.CompletedTripsFragment;
import com.itcinfotech.ssfpartner.cabdriver.UpComingTripsFragment;
import com.itcinfotech.ssfpartner.food.FoodDashboardFragment;
import com.itcinfotech.ssfpartner.food.FoodHistoryFragment;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

public class MainActivity extends AppCompatActivity {


    private TextView mToolbarTitle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    if(SessionManager.getInstance(MainActivity.this).getIsCabDriver()){
                        updateTitle("UpComing Trips");
                        loadUpComingTripsFragment();
                    }
                    else {
                        updateTitle("Order Details");
                        loadFoodDashboardFragment();
                    }

                    return true;
                case R.id.navigation_dashboard:

                    updateTitle("History");
                    if(SessionManager.getInstance(MainActivity.this).getIsCabDriver()){
                        loadCompletedTripsFragment();
                    }
                    else {
                        loadFoodHistoryFragment();
                    }

                    return true;
                case R.id.navigation_notifications:
                    updateTitle("Profile");
                    loadProfileFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbarTitle = findViewById(R.id.toolbar_title);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    private void updateTitle(String title){
        mToolbarTitle.setText(title);
    }

    private void loadUpComingTripsFragment() {
        UpComingTripsFragment fragment = new UpComingTripsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private void loadCompletedTripsFragment() {
        CompletedTripsFragment fragment = new CompletedTripsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private void loadFoodDashboardFragment() {
        FoodDashboardFragment fragment = new FoodDashboardFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private void loadFoodHistoryFragment() {
        FoodHistoryFragment fragment = new FoodHistoryFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private void loadProfileFragment() {
        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
    }

    private void checkTokenStatus(){
        if(!SessionManager.getInstance(this).isTokenUpdated()){

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) {
                        Utility.printLog("getInstanceId failed"+ task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    String token = task.getResult().getToken();
                    Utility.printLog("getInstanceId token = "+token);
                    sendRegistrationToServer(token);
                }
            });
        }
    }

    private void sendRegistrationToServer(String token) {

        /*UpdateRegisterTokenRequest request = new UpdateRegisterTokenRequest(SessionManager.getInstance(this).getPSID(), token,"",true);
        Utility.printLog("response = "+request.toString());

        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<ApprovalsHelper> call = apiService.updateToken(SessionManager.getInstance(MainActivity.this).getSessionToken(), request);
        call.enqueue(new Callback<ApprovalsHelper>() {
            @Override
            public void onResponse(Call<ApprovalsHelper> call, Response<ApprovalsHelper> response) {

                Utility.printLog("response = "+response.body().toString());
                if (response != null && response.isSuccessful()) {
                    if (response.body() != null) {
                        SessionManager.getInstance(MainActivity.this).setTokenUpdated(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<ApprovalsHelper> call, Throwable t) {
                Utility.printLog("response error "+t.getMessage());

            }
        });*/
    }
}
