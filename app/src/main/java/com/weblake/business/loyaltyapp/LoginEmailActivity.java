package com.weblake.business.loyaltyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;

import cz.msebera.android.httpclient.Header;

public class LoginEmailActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    EditText loginPasswordConfirm;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        findViewByIdAll();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email"); //if it's a string you stored.
        loginEmail.setText(email);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = loginPassword.getText().toString();
                String passwordConfirm = loginPasswordConfirm.getText().toString();
                if(password.equals(passwordConfirm)){
                    RequestParams params= new RequestParams();
                    params.put("Email",loginEmail.getText().toString());
                    params.put("Password",password);
                    invokeWS(params);
                }else {
                    Toast.makeText(getApplicationContext(), "Mots de passe n'est pas identique", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    private void findViewByIdAll() {
        loginEmail=(EditText) findViewById(R.id.loginEmail);
        loginPassword=(EditText) findViewById(R.id.loginPassword);
        loginPasswordConfirm=(EditText) findViewById(R.id.loginPasswordConfim);
        loginButton = (Button) findViewById(R.id.loginBTConnexion);
    }

}
