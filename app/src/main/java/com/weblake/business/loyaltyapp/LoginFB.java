/*
package com.weblake.business.loyaltyapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginFB {
    private String email;
    private String id;
    private String fname;
    private String lname;

    private LoginButton loginButton;
    private String response;
    private CallbackManager callbackManager;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;


    public LoginFB(){

    }
    public  LoginFB(final Context context, LoginButton loginButton) {
        this.loginButton=loginButton;
        Log.d("LoginActivity", "before22");
        callbackManager = CallbackManager.Factory.create();



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //Log.d("stats1",newProfile.getFirstName());
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));
        Log.d("LoginActivity", "before1");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LoginActivity", "before0");
              */
/*  AccessToken accessToken = loginResult.getAccessToken();
                Log.d("LoginActivity", "before");
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {
                        Log.d("LoginActivity", object.toString());
                        Log.d("LoginActivity2", response.toString());
                        email = object.opt("email").toString();
                        fname = object.opt("name").toString();
                        id = object.opt("id").toString();
                        Toast.makeText(context, "name : "+fname+" email : "+email, Toast.LENGTH_LONG).show();
                        //response="success";
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();*//*


            }

            @Override
            public void onCancel() {
                Log.d("LoginActivity", "onCancel");
                response="false";
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LoginActivity", "onError");
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                response="false";
            }
        });
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
*/
package com.weblake.business.loyaltyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginFB extends AppCompatActivity {
    private String email;
    private String id;
    private String fname;
    private String lname;

    private TextView info;
    private TextView info_profil;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login_fb);

        info = (TextView)findViewById(R.id.info);
        info_profil = (TextView)findViewById(R.id.info_profil);
        loginButton = (LoginButton)findViewById(R.id.login_button);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                //Log.d("stats1",newProfile.getFirstName());
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LoginActivity", "before0");

                AccessToken accessToken = loginResult.getAccessToken();
                Log.d("LoginActivity", "before");
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object,GraphResponse response) {
                        Log.d("LoginActivity", object.toString());
                        Log.d("LoginActivity2", response.toString());
                        email = object.opt("email").toString();
                        fname = object.opt("name").toString();
                        id = object.opt("id").toString();
                        Log.d("LoginActivity2Email : ", email);
                        //response="success";
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                Log.d("LoginActivity", "onCancel");
               // response="false";
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("LoginActivity", "onError");
               /* Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                response="false";*/
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        //Log.d("stats",profile.getFirstName());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }
}
