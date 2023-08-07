package com.mtb.myapplication;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button main_ou_btn,
            main_static_btn;
    WebView main_web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();
        ouClick();
        staticClick();
    }

    private void initial() {
        main_ou_btn = findViewById(R.id.main_ou_btn);
        main_static_btn = findViewById(R.id.main_static_btn);
        main_web_view = findViewById(R.id.main_web_view);
    }

    private void staticClick() {
        main_static_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String html = String.join("",
                        "",
                        "<html>",
                        "<head></head>",
                        "<body",
                        "<h1 style='color:red'>IT - OU ers</h1>",
                        "<h2>WebView example</h2>",
                        "<h3>Class: CS2001</h3>",
                        "<h4>Semester: 223</h4>",
                        "</body",
                        "</html>");

                main_web_view.loadData(html, "text/html", "UTF-8");
            }
        });
    }

    private void ouClick() {
        main_ou_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                main_web_view.setWebViewClient(new WebViewClient());
                String url = "https://www.facebook.com/";
                main_web_view.getSettings().setJavaScriptEnabled(true);
                main_web_view.loadUrl(url);
            }
        });
    }

}