package com.beetrack.leandronanni.android_beetrack.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.beetrack.leandronanni.android_beetrack.R;

public class WebViewActivity extends AppCompatActivity {

    public static final String URL = "URL";

    private WebView webView;

    private ProgressBar progress;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.webview);

        //noinspection ConstantConditions
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.loadUrl(getIntent().getStringExtra(URL));
    }
}
