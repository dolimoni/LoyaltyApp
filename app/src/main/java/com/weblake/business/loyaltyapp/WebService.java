package com.weblake.business.loyaltyapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

/**
 * Created by SERIALIZABLE on 04/04/2017.
 */

public class WebService {
    private RequestParams params;
    //example : http://cartedevisite.ma/test/register.php
    private String url;
    private Context context;
    private boolean emailAlreadyExists=false;

    public boolean isEmailAlreadyExists() {
        return emailAlreadyExists;
    }

    public void setEmailAlreadyExists(boolean emailAlreadyExists) {
        this.emailAlreadyExists = emailAlreadyExists;
    }

    public boolean isLoginSucces() {
        return loginSucces;
    }

    public void setLoginSucces(boolean loginSucces) {
        this.loginSucces = loginSucces;
    }

    boolean loginSucces = false;
    public WebService(){

    }

    public WebService(RequestParams params, String url, Context context) {
        this.params = params;
        this.url = url;
        this.context = context;
    }

    public RequestParams getParams() {
        return params;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void registerByEmail(RequestParams params,String url,final Context context){
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();


        client.post(url,params ,new JsonHttpResponseHandler() {

            @Override
            public void onProgress(long bytesWritten, long totalSize) {


            }


            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Hide Progress Dialog

                try {
                    Log.i(WebService.this.toString(),"Server response  : ----> "+response);

                    // JSON Object
                    JSONObject obj = response;
                    Log.i(WebService.this.toString(),"Server response  : ----> "+response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls

                        // Display successfully registered message using Toast
                        Log.i(WebService.this.toString(),"You are successfully registered!");

                        Intent startActivity = new Intent();
                        startActivity.setClass(context, MainActivity.class);
                        startActivity.setAction(MainActivity.class.getName());
                        startActivity.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

                        context.startActivity(startActivity);
                        Toast.makeText(context, "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{
                        Log.i(WebService.this.toString(),"Error Occured : "+obj.getString("response"));
                        Toast.makeText(context, obj.getString("response"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    Log.i(WebService.this.toString(),"JSONExeption : ---->"+e.getMessage());
                    Toast.makeText(context, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers,String responseString, Throwable error) {
                // Hide Progress Dialog
                // When Http response code is '404'

                Log.i("ws", "---->>onFailure : " + error);
                Log.i("ws", "---->>onFailure : " +responseString);
                if(statusCode == 404){
                    Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(context, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public Map<String,Object>
       isEmailAlreadyExists(final RequestParams params, String url, final Context context,final String email) {
        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        Map<String,Object> response = new HashMap<String,Object>();


        client.post("http://cartedevisite.ma/test/emailAlreadyExists.php",params ,new JsonHttpResponseHandler() {

            @Override
            public void onProgress(long bytesWritten, long totalSize) {


            }


            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Hide Progress Dialog

                try {
                    Log.i(WebService.this.toString(),"Server response  : ----> "+response);

                    Intent startActivity = new Intent();
                    startActivity.setClass(context, LoginEmailActivity.class);
                    startActivity.setAction(LoginEmailActivity.class.getName());
                    startActivity.setFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);


                    // JSON Object
                    JSONObject obj = response;
                    Log.i(WebService.this.toString(),"Server response  : ----> "+response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        emailAlreadyExists=true;
                        startActivity.putExtra("emailAlreadyExists",true);
                        startActivity.putExtra("email",email);
                        Log.i(WebService.this.toString(),"Start Activiti");
                        context.startActivity(startActivity);
                        Log.i(WebService.this.toString(),"Email inexistant"+obj.getString("response"));
                    }
                    // Else display error message
                    else{
                        Log.i(WebService.this.toString(),"Error Occured : "+obj.getString("response"));
                        if("Email inexistant !".equals(obj.getString("response"))){
                            startActivity.putExtra("emailAlreadyExists",false);
                            startActivity.putExtra("email",email);



                            context.startActivity(startActivity);
                        }else{
                            Toast.makeText(context,obj.getString("response") , Toast.LENGTH_LONG).show();
                        }

                        emailAlreadyExists=false;

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    Log.i(WebService.this.toString(),"JSONExeption : ---->"+e.getMessage());
                    Toast.makeText(context, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers,String responseString, Throwable error) {
                // Hide Progress Dialog
                // When Http response code is '404'

                Log.i("ws", "---->>onFailure : " + error);
                Log.i("ws", "---->>onFailure : " +responseString);
                if(statusCode == 404){
                    Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(context, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
        return response;
    }


    public boolean login(RequestParams params, String url, Context applicationContext) {

        // Show Progress Dialog
        //prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();


        client.post(url,params ,new JsonHttpResponseHandler() {

            @Override
            public void onProgress(long bytesWritten, long totalSize) {


            }


            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Hide Progress Dialog

                try {
                    Log.i(WebService.this.toString(),"Server response  : ----> "+response);

                    // JSON Object
                    JSONObject obj = response;
                    Log.i(WebService.this.toString(),"Server response  : ----> "+response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls
                        loginSucces=true;

                        Intent startActivity = new Intent();
                        startActivity.setClass(context, MainActivity.class);
                        startActivity.setAction(MainActivity.class.getName());
                        startActivity.setFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        context.startActivity(startActivity);
                    }
                    // Else display error message
                    else{
                        Log.i(WebService.this.toString(),"Error Occured : "+obj.getString("response"));
                        loginSucces=false;
                        Toast.makeText(context, "Email ou password incorrect", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    Log.i(WebService.this.toString(),"JSONExeption : ---->"+e.getMessage());
                    Toast.makeText(context, "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }
            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers,String responseString, Throwable error) {
                // Hide Progress Dialog
                // When Http response code is '404'

                Log.i("ws", "---->>onFailure : " + error);
                Log.i("ws", "---->>onFailure : " +responseString);
                if(statusCode == 404){
                    Toast.makeText(context, "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(context, "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(context, "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
        return loginSucces;
    }
}
