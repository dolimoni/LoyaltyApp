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

import cz.msebera.android.httpclient.Header;

/**
 * Created by SERIALIZABLE on 04/04/2017.
 */

public class InvokeWS {
    public void invokeWS(String url, final Context context, RequestParams params){
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
                    Log.i(InvokeWS.this.toString(),"Server response  : ----> "+response);

                    // JSON Object
                    JSONObject obj = response;
                    Log.i(InvokeWS.this.toString(),"Server response  : ----> "+response);
                    // When the JSON response has status boolean value assigned with true
                    if(obj.getBoolean("status")){
                        // Set Default Values for Edit View controls

                        // Display successfully registered message using Toast
                        Log.i(InvokeWS.this.toString(),"You are successfully registered!");
                        Intent intent = new Intent(context, LoginEmailActivity.class);

                        context.startActivity(intent);
                        Toast.makeText(context, "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }
                    // Else display error message
                    else{
                        Log.i(InvokeWS.this.toString(),"Error Occured : "+obj.getString("response"));
                        Toast.makeText(context, obj.getString("response"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block

                    Log.i(InvokeWS.this.toString(),"JSONExeption : ---->"+e.getMessage());
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
}
