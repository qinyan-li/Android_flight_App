package com.glasses.flightapp.flightapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class DisplaySurveyFormsActivity extends NavigationActivity {
    private WebView survey;
    private ProgressBar progress;

    private String url = "https://docs.google.com/forms/d/e/1FAIpQLSfYhmjxShqnHxi59Jk-kp5e-1T3vxibfPKTJXgFJUgJ0kuUwA/viewform";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_survey_forms);
        initNavigation();

        survey = findViewById(R.id.webview);
        progress = findViewById(R.id.web_progress);

        survey.getSettings().setJavaScriptEnabled(true);
        survey.getSettings().setSupportZoom(true);
        survey.getSettings().setUseWideViewPort(true);
        survey.getSettings().setBuiltInZoomControls(true);

        survey.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                progress.setVisibility(View.GONE);
            }
        });

        survey.loadUrl(url);
    }
}