package com.itcinfotech.ssfpartner.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itcinfotech.ssfpartner.R;
import com.itcinfotech.ssfpartner.network.SSFApiClient;
import com.itcinfotech.ssfpartner.network.SSFApiInterface;
import com.itcinfotech.ssfpartner.pojo.user.LoginRequest;
import com.itcinfotech.ssfpartner.pojo.user.LoginResponse;
import com.itcinfotech.ssfpartner.utility.SessionManager;
import com.itcinfotech.ssfpartner.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTitle,mLoginHere,mRegisterHere;
    private EditText mLoginPhoneNumber,mLoginPassword;
    private EditText mFullName,mPhoneNumber,mEmail,mPassword;
    private Button mLogin,mRegister;
    private LinearLayout mLoginLayout,mRegisterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SessionManager.getInstance(LoginActivity.this).isLoggedIn()) {
            loadHomePage();
        }

        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews(){

        mTitle = findViewById(R.id.toolbar_title);
        mTitle.setText("Register");

        mLoginHere = findViewById(R.id.tv_login);
        mRegisterHere = findViewById(R.id.tv_register);
        mLoginPhoneNumber = findViewById(R.id.et_login_phone_number);
        mLoginPassword = findViewById(R.id.et_login_password);
        mFullName = findViewById(R.id.et_name);
        mPhoneNumber = findViewById(R.id.et_phone_number);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        mLogin = findViewById(R.id.btn_login);
        mRegister = findViewById(R.id.btn_register);
        mLoginLayout = findViewById(R.id.layout_login);
        mRegisterLayout = findViewById(R.id.layout_register);

        mLoginHere.setOnClickListener(this);
        mRegisterHere.setOnClickListener(this);
        mLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_login:
                mLoginLayout.setVisibility(View.VISIBLE);
                mRegisterLayout.setVisibility(View.GONE);
                mTitle.setText("Login");
                break;
            case R.id.tv_register:
                mLoginLayout.setVisibility(View.GONE);
                mRegisterLayout.setVisibility(View.VISIBLE);
                mTitle.setText("Register");
                break;
            case R.id.btn_login:

                if(mLoginPhoneNumber.getText().toString().trim().length()>0){

                    if(mLoginPassword.getText().toString().trim().length()>0){

                        if (Utility.isNetworkAvailable(LoginActivity.this)) {
                            LoginRequest request = new LoginRequest();
                            request.setPhoneNumber(mLoginPhoneNumber.getText().toString().trim());
                            request.setPassword(mLoginPassword.getText().toString().trim());
                            loginUser(request);
                        } else {
                            Utility.showDialog(LoginActivity.this, "Please check your internet connection");
                        }
                    }
                    else {
                        mLoginPassword.setError("Enter password");
                    }
                }
                else {
                    mLoginPhoneNumber.setError("Enter phone number");
                }

                break;
            case R.id.btn_register:
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
        }
    }

    private void loginUser(LoginRequest request) {

        Utility.showProgresDialog(LoginActivity.this);
        SSFApiInterface apiService = SSFApiClient.getClient().create(SSFApiInterface.class);
        Call<LoginResponse> call = apiService.loginUser(SessionManager.getInstance(LoginActivity.this).getSessionToken(), request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Utility.dismessProgresDialog();

                try {
                    if (response != null && response.isSuccessful()) {
                        if (response.body() != null && response.body().getIssuccess()) {

                            if(response.body().getResult()!=null){
                                SessionManager.getInstance(LoginActivity.this).setIsLogin(true);
                                SessionManager.getInstance(LoginActivity.this).setUserName(response.body().getResult().getUserName());
                                SessionManager.getInstance(LoginActivity.this).storePSID(response.body().getResult().getID());
                                SessionManager.getInstance(LoginActivity.this).setIsCabDriver(response.body().getResult().getIsDriver());

                                loadHomePage();
                            }
                            else {
                                Utility.showErrorAlert(LoginActivity.this);
                            }
                        }
                        else {
                            Utility.showErrorAlert(LoginActivity.this);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Utility.showErrorAlert(LoginActivity.this);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Utility.dismessProgresDialog();
                Utility.printLog("response error " + t.getMessage());
            }
        });
    }

    private void loadHomePage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
