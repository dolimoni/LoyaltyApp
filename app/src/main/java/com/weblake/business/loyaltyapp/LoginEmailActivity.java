package com.weblake.business.loyaltyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

public class LoginEmailActivity extends AppCompatActivity {

    EditText loginEmail;
    EditText loginPassword;
    EditText loginPasswordConfirm;
    Button loginButton;
    boolean login=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);
        findViewByIdAll();

        Intent intent = getIntent();
        String email = intent.getStringExtra("email"); //if it's a string you stored.
       loginEmail.setText(email);
        if(intent.getBooleanExtra("emailAlreadyExists",false)){
            loginPasswordConfirm.setVisibility(View.INVISIBLE);
            login=true;
        }

       Log.d("login : email",email);

        Log.d("emailAlreadyExists",""+intent.getBooleanExtra("emailAlreadyExists",false));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = loginPassword.getText().toString();
                String passwordConfirm = loginPasswordConfirm.getText().toString();
                if(password.equals(passwordConfirm) || login){
                    RequestParams params= new RequestParams();
                    params.put("Email",loginEmail.getText().toString());
                    params.put("Password",password);
                    String url = "http://cartedevisite.ma/test/registerByEmail.php";
                    WebService webService = new WebService(params, url, getApplicationContext());
                    if(login){
                         url = "http://cartedevisite.ma/test/loginByEmailAndPassword.php";

                        webService.login(params, url, getApplicationContext());

                    }else {
                        webService.registerByEmail(params, url, getApplicationContext());
                    }



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
