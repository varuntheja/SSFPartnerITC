package com.itcinfotech.ssfpartner.food;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.utility.Constants;

public class VerifyDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private IntentIntegrator qrScan;
    private LinearLayout mStatusLayout;
    private ImageView mStatusImage;
    private String type, PSID = "30193";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_details);

        mStatusLayout = findViewById(R.id.layout_user_status);
        mStatusImage = findViewById(R.id.user_status);
        findViewById(R.id.iv_scan_qr_code).setOnClickListener(this);
        findViewById(R.id.btn_verify_user).setOnClickListener(this);
        qrScan = new IntentIntegrator(this);

        type = getIntent().getStringExtra(Constants.EXTRA_TYPE);

        if(type.equalsIgnoreCase(Constants.EXTRA_BREAKFAST)){

        }
        else if(type.equalsIgnoreCase(Constants.EXTRA_LUNCH)){

        }
        else if(type.equalsIgnoreCase(Constants.EXTRA_DINNER)){

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.iv_scan_qr_code:
                qrScan.initiateScan();
                break;
            case R.id.btn_verify_user:



                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {

                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();


                    mStatusLayout.setVisibility(View.VISIBLE);
                    if(result.getContents().equalsIgnoreCase(PSID)){
                        mStatusImage.setBackgroundResource(R.drawable.ic_verified);
                    }
                    else {
                        mStatusImage.setBackgroundResource(R.drawable.ic_close);
                    }
                    mStatusImage.setBackgroundResource(R.drawable.ic_verified);

                    //converting the data to json
                    //JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    //textViewName.setText(obj.getString("name"));
                    //textViewAddress.setText(obj.getString("address"));
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
