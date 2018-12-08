package com.itcinfotech.ssfpartner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTitle,mLoginHere,mRegisterHere;
    private EditText mLoginPhoneNumber,mLoginPassword;
    private EditText mFullName,mPhoneNumber,mEmail,mPassword;
    private Button mLogin,mRegister;
    private LinearLayout mLoginLayout,mRegisterLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
            case R.id.btn_register:
                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_two, R.anim.anim_one);
                break;
        }
    }
}
