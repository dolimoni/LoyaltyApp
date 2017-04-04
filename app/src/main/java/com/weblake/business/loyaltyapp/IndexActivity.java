package com.weblake.business.loyaltyapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.logging.Level;
import java.util.logging.Logger;

import cz.msebera.android.httpclient.Header;

/**
 *
 * Register Activity Class
 */
public class IndexActivity extends Activity {
    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Email Edit View Object
    //EditText emailET;
    //Validation Button
    Button indexBTFB;
    Button indexBTConnexion;
    LoginButton loginButton;
    EditText indexETEmail;
    Logger logger = Logger.getLogger(IndexActivity.class.getName());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_index);
        loginButton=(LoginButton) findViewById(R.id.login_button);
        indexBTConnexion=(Button) findViewById(R.id.indexBTConnexion);
        indexETEmail=(EditText) findViewById(R.id.indexETEmail);
       /* // Find Email Edit View control by ID
        emailET = (EditText)findViewById(R.id.);*/
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        //validatiIB = (ImageButton)findViewById(R.id.indexValidate);

    /*    loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFB loginFB = new LoginFB(getApplicationContext(), loginButton);



            }
        });*/
        /*indexBTFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });*/

        indexBTConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getApplicationContext(), "You are successfully registered! "+email, Toast.LENGTH_LONG).show();
                RequestParams params = new RequestParams();
                params.put("Email",indexETEmail.getText().toString());
                WebService webService = new WebService();
                webService.setParams(params);
                webService.setContext(getApplicationContext());


                // return
                webService.isEmailAlreadyExists(params,"http://cartedevisite.ma/test/emailAlreadyExists.php",getApplicationContext(),indexETEmail.getText().toString());

                /*if(!emailExists){
                    Log.d("emailAlreadyExists","Yes");
                    intent.putExtra("emailAlreadyExists",true);
                }else {

                    Log.d("emailAlreadyExists","No");
                    intent.putExtra("emailAlreadyExists",false);
                }*/


            }
        });


    }


    public void registerUser(){
        // Get Email ET control value
        //String email = emailET.getText().toString();
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams();
        // When Name Edit View, Email Edit View and Password Edit View have values other than Null
        if(Utility.isNotNull("email")){
            // When Email entered is Valid
            if(Utility.validate("email") || true){
                // Put Http parameter username with value of Email Edit View control
                params.put("FName", "khalid");
                params.put("LName", "ESSALHI");
                params.put("Email", "khalid4@email.com");
                // Invoke RESTful Web Service with Http parameters
                invokeWS(params);
            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        }
        // When any of the Edit View control left blank
        else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param params
     */
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        logger.log(Level.FINE,"Trying to reach server!");

        client.post("http://cartedevisite.ma/test/register.php",params ,new JsonHttpResponseHandler() {

            @Override
            public void onProgress(long bytesWritten, long totalSize) {

                prgDialog.show();
            }


            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    Log.i(IndexActivity.this.toString(),"Server response  : ----> "+response);

                    // JSON Object
                    JSONObject obj = response;
                    Log.i(IndexActivity.this.toString(),"Server response  : ----> "+response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        setDefaultValues();
                        // Display successfully registered message using Toast
                        Log.i(IndexActivity.this.toString(),"You are successfully registered!");
                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{
                        Log.i(IndexActivity.this.toString(),"Error Occured : "+obj.getString("response"));
                        Toast.makeText(getApplicationContext(), obj.getString("response"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    logger.log(Level.FINE,"Error Occured!");
                    Log.i(IndexActivity.this.toString(),"JSONExeption : ---->"+e.getMessage());
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers,String responseString, Throwable error) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'

                Log.i("ws", "---->>onFailure : " + error);
                Log.i("ws", "---->>onFailure : " +responseString);
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Method which navigates from Register Activity to Login Activity
     */
    public void navigatetoLoginActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(),MainActivity.class);
        // Clears History of Activity
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    /**
     * Set degault values for Edit View controls
     */
    public void setDefaultValues(){
        /*emailET.setText("");*/
    }



}