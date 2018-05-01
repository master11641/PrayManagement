package com.example.alireza.myapplication.config;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

/**
 * Created by alireza on 4/18/2018.
 */

public class FirebaseInstanceID extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        if(refreshedToken!=null){
           // Toast.makeText(getApplicationContext(),refreshedToken,Toast.LENGTH_LONG).show();
        }

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
      //  sendRegistrationToServer(refreshedToken);
    }

}
