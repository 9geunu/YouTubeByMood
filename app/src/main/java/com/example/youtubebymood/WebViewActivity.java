package com.example.youtubebymood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    WebSettings webSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient());

        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String videoID = getIntent().getStringExtra("video_id");
        webView.loadUrl("https://www.youtube.com/watch?v=" + videoID);
    }
}