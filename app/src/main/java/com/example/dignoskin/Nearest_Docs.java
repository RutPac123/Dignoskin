package com.example.dignoskin;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class Nearest_Docs extends AppCompatActivity {

   private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest__docs);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:(function() { " +
                        "document.getElementById('footer').style.display='none'; " +
                        "document.getElementsByClassName('logo')[0].style.display='none'; " +
                        "})()");
            }
        });
        webView.loadUrl("https://www.google.com/search?q=dermatologist+near+me&rlz=1C1CHBD_enIN814IN814&oq=dermatologist+near+me&aqs=chrome.0.0l8.2618j0j7&sourceid=chrome&ie=UTF-8");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }
}
