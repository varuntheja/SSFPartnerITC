package com.itcinfotech.ssfpartner.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itcinfotech.ssfpartner.R;

import java.util.ArrayList;
import java.util.List;

public class PassengersAdapter extends RecyclerView.Adapter<PassengersAdapter.MyViewHolder> {

    private List<String> categoryList = new ArrayList<>();
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //private TextView mName, mPSID, mRequest;

        private MyViewHolder(View view) {
            super(view);
            /*mName = view.findViewById(R.id.tv_name);
            mPSID = view.findViewById(R.id.tv_psid);
            mRequest = view.findViewById(R.id.request_desc);*/
        }
    }

    public PassengersAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.categoryList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}


