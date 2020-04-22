package com.example.gitcontacts;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class GithubPageActivity extends AppCompatActivity {
    private WebView wbGithubPage;
    private SharedPreferences sharedPreferences;
    private  String current_username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_github);

        sharedPreferences = getSharedPreferences("sharedP", Context.MODE_PRIVATE);
        current_username = sharedPreferences.getString("usernamepage","Missing Username");
//        Intent intent = getIntent();
//        String username = intent.getStringExtra("username");
//        Log.d("Marcelo", "onCreate3: "+ username);
        wbGithubPage = findViewById(R.id.wbGithubPage);
        wbGithubPage.setWebViewClient(new WebViewClient());

        wbGithubPage.loadUrl("https://github.com/" + current_username);


    }
}
