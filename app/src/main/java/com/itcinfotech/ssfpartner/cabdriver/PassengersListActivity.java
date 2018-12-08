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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.adapters.PassengersAdapter;
import com.itcinfotech.ssfpartner.utility.RecyclerTouchListener;
import com.itcinfotech.ssfpartner.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class PassengersListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_passengers_list);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Passengers List");
        Utility.applyFontForToolbarTitle(this, mToolbar);

        RecyclerView recyclerView = findViewById(R.id.passengers_recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        List<String> list= new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("a");
        list.add("a");

        PassengersAdapter adapter = new PassengersAdapter(this, list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(PassengersListActivity.this, MapLocationsActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
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
