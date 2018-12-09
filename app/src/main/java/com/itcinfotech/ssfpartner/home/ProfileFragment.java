package com.itcinfotech.ssfpartner.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.utility.SessionManager;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.tv_signOut).setOnClickListener(this);

        ((TextView)view.findViewById(R.id.tv_name)).setText(SessionManager.getInstance(getActivity()).getUserName());
        ((TextView)view.findViewById(R.id.tv_psid)).setText("ID : "+SessionManager.getInstance(getActivity()).getPSID());
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.tv_signOut){

            SessionManager.getInstance(getActivity()).clearSharedPreferences();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
