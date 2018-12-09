package com.itcinfotech.ssfpartner.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.network.SSFApiClient;
import com.itcinfotech.ssfpartner.network.SSFApiInterface;
import com.itcinfotech.ssfpartner.pojo.food.VerifyUserRequest;
import com.itcinfotech.ssfpartner.pojo.food.VerifyUserResponse;
import com.itcinfotech.ssfpartner.utility.Constants;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private IntentIntegrator qrScan;
    private EditText mPsidET;
    private TextView mName,mPsid;
    private LinearLayout mStatusLayout;
    private ImageView mStatusImage,mUserImage;
    private String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_details);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Verify Details");
        Utility.applyFontForToolbarTitle(this, mToolbar);

        mPsidET = findViewById(R.id.et_psid);
        mUserImage = findViewById(R.id.img_user);
        mName = findViewById(R.id.tv_name);
        mPsid = findViewById(R.id.tv_psid);
        mStatusLayout = findViewById(R.id.layout_user_status);
        mStatusImage = findViewById(R.id.user_status);
        findViewById(R.id.iv_scan_qr_code).setOnClickListener(this);
        findViewById(R.id.btn_verify_user).setOnClickListener(this);
        qrScan = new IntentIntegrator(this);

        type = getIntent().getStringExtra(Constants.EXTRA_TYPE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_scan_qr_code:
                qrScan.initiateScan();
                break;
            case R.id.btn_verify_user:

                mStatusLayout.setVisibility(View.GONE);

                if(mPsidET.getText().toString().trim().length()>0){

                    verifyUser(mPsidET.getText().toString().trim());
                }
                else {
                    mPsidET.setError("Enter PSID");
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                try {
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    if(Utility.isNetworkAvailable(VerifyDetailsActivity.this)){

                        verifyUser(result.getContents());
                    }
                    else {
                        Utility.showDialog(VerifyDetailsActivity.this, "Please check your internet connection");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void verifyUser(String psid) {

        VerifyUserRequest request = new VerifyUserRequest();
        request.setDateTime(Utility.getCurrentDate("yyyy-MM-dd")+"T00:00:00");
        request.setPsid(Integer.parseInt(psid));
        if(type.equalsIgnoreCase(Constants.EXTRA_BREAKFAST)){
            request.setFoodTiming(1);
        }
        else if(type.equalsIgnoreCase(Constants.EXTRA_LUNCH)){
            request.setFoodTiming(2);
        }
        else if(type.equalsIgnoreCase(Constants.EXTRA_DINNER)){
            request.setFoodTiming(3);
        }

        Utility.showProgresDialog(VerifyDetailsActivity.this);
        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<VerifyUserResponse> call = apiService.verifyUser(SessionManager.getInstance(VerifyDetailsActivity.this).getSessionToken(), request);
        call.enqueue(new Callback<VerifyUserResponse>() {
            @Override
            public void onResponse(Call<VerifyUserResponse> call, Response<VerifyUserResponse> response) {

                Utility.dismessProgresDialog();
                if (response != null && response.isSuccessful()) {
                    if (response.body() != null && response.body().getIssuccess()) {

                        try {
                            mPsidET.setText("");
                            mName.setText(response.body().getResult().getName());
                            mPsid.setText("PSID : "+response.body().getResult().getPSID());
                            mStatusLayout.setVisibility(View.VISIBLE);

                            String imageUrl = "https://i3lidesnwg.itcinfotech.com/ssfapi/Asserts/image/"+response.body().getResult().getPSID()+".jpg";
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.ic_account_grey);
                            Glide.with(VerifyDetailsActivity.this)
                                    .setDefaultRequestOptions(requestOptions)
                                    .load(imageUrl).into(mUserImage);

                            if(response.body().getResult()!=null && response.body().getResult().getPSID()!=null){
                                mStatusImage.setBackgroundResource(R.drawable.ic_verified);
                            }
                            else {
                                mStatusImage.setBackgroundResource(R.drawable.ic_close);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Utility.showErrorAlert(VerifyDetailsActivity.this);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyUserResponse> call, Throwable t) {
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

        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.anim_three, R.anim.anim_four);
    }
}
