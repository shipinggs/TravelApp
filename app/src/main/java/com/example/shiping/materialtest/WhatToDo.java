package com.example.shiping.materialtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Activity that display a Webview showing the relevant information about a tourist attraction,
 * which is passes into this activity as an Extra in the Intent when the activity is launched
 */

public class WhatToDo extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_to_do);
        Intent myIntent = getIntent();
        String location = myIntent.getStringExtra("location");
        initiate(location);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(location);
        setSupportActionBar(toolbar);
    }

    /**
     * Open a URL stored in TodoData corresponding to the location of attraction.
     * Enable JavaScript to display dynamic contents.
     * @param location tourist attraction to be shown on the Webview
     */

    private void initiate(String location) {
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl((new TodoData()).toDoList.get(location));
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}
