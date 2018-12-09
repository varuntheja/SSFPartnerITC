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
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResult;
import com.itcinfotech.ssfpartner.utility.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class CompletedTripsFragment extends Fragment{

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
        mNoOrders.setVisibility(View.VISIBLE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new CabTripsAdapter(getActivity(), tripsList);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(getActivity(), PassengersListActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
