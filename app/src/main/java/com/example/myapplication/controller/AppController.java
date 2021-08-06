package com.example.myapplication.controller;

import android.app.Application;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    //this is a Singleton

    private static AppController instance;
    private RequestQueue requestQueue;

//    Don't need it
//    private ImageLoader imageLoader;

    public static synchronized AppController getInstance() {
//        if (instance == null){
//            instance = new AppController();
//        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            //getApplicationContext is a key, it keep you from leaking
            //Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    //when create set to this current instance
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
