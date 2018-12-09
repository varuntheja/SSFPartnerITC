package com.itcinfotech.ssfpartner.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.cabdriver.MapLocationsActivity;
import com.itcinfotech.ssfpartner.cabdriver.PassengersListActivity;
import com.itcinfotech.ssfpartner.pojo.cab.PassengersResult;

import java.util.ArrayList;
import java.util.List;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.MyViewHolder> {

    private List<PassengersResult> passengersList = new ArrayList<>();
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mFrom, mTo,mPhone,mCall,mNavigate;

        private MyViewHolder(View view) {
            super(view);
            mName = view.findViewById(R.id.tv_passenger_name);
            mFrom = view.findViewById(R.id.tv_from_location);
            mTo = view.findViewById(R.id.tv_to_location);
            mPhone = view.findViewById(R.id.tv_phone);
            mCall = view.findViewById(R.id.tv_call);
            mNavigate = view.findViewById(R.id.tv_navigate);
        }
    }

    public PassengersAdapter(Context context, List<PassengersResult> list) {
        this.mContext = context;
        this.passengersList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        final PassengersResult passenger = passengersList.get(position);
        holder.mName.setText(passenger.getName());
        holder.mFrom.setText("From : "+passenger.getFrom());
        holder.mTo.setText("To : "+passenger.getTo());
        holder.mPhone.setText("Phone : "+passenger.getPhoneNo());

        holder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(passenger.getPhoneNo()!=null && passenger.getPhoneNo().length()>0)
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", passenger.getPhoneNo(), null));
                    mContext.startActivity(intent);
                }
            }
        });

        holder.mNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, MapLocationsActivity.class);
                intent.putExtra("LatLng", passenger.getTolatLong());
                intent.putExtra("NAME", passenger.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return passengersList.size();
    }
}


