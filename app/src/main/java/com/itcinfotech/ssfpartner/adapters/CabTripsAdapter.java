package com.itcinfotech.ssfpartner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResponse;
import com.itcinfotech.ssfpartner.pojo.cab.UpComingTripsResult;
import com.itcinfotech.ssfpartner.utility.Utility;

import java.util.ArrayList;
import java.util.List;

public class CabTripsAdapter extends RecyclerView.Adapter<CabTripsAdapter.MyViewHolder> {

    private List<UpComingTripsResult> categoryList = new ArrayList<>();
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mFrom, mTo, mDate;

        private MyViewHolder(View view) {
            super(view);
            mFrom = view.findViewById(R.id.tv_from_location);
            mTo = view.findViewById(R.id.tv_to_location);
            mDate = view.findViewById(R.id.tv_date);
        }
    }

    public CabTripsAdapter(Context context, List<UpComingTripsResult> list) {
        this.mContext = context;
        this.categoryList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        UpComingTripsResult tripDetails = categoryList.get(position);
        holder.mFrom.setText("From : "+tripDetails.getFROM());
        holder.mFrom.setText("To : "+tripDetails.getTO());
        holder.mFrom.setText("Date : "+ Utility.changeDateFormat(tripDetails.getDATE(), "yyyy-MM-dd'T'HH:mm:ss", "dd-MMM-yyyy HH:mm"));

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}

