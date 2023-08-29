package com.mtb.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView1;
    Button exitBtn, traditionalBtn;
    String[] colors = {"Red", "Orange", "Green"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        textView1 = findViewById(R.id.textView1);
        exitBtn = findViewById(R.id.exitBtn);
        traditionalBtn = findViewById(R.id.traditionalBtn);
    }

    private void bindData() {
        Activity context = MainActivity.this;

        exitBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("Thoát")
                    .setMessage("Bạn muốn thoát chương trình?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                            // txtAlert.setText("YES " + Integer.toString(which));
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // txtAlert.setText("CANCEL " + Integer.toString(which));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // txtAlert.setText("NO " + Integer.toString(which));
                        }
                    })
                    .create()
                    .show();
        });

        traditionalBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Pick a color").setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            textView1.setBackgroundColor(Color.parseColor("#e74c3c"));
                            textView1.setTextColor(Color.parseColor("#ecf0f1"));
                            break;
                        case 1:
                            textView1.setBackgroundColor(Color.parseColor("#f39c12"));
                            textView1.setTextColor(Color.parseColor("#34495e"));
                            break;
                        case 2:
                            textView1.setBackgroundColor(Color.parseColor("#16a085"));
                            textView1.setTextColor(Color.parseColor("#ecf0f1"));
                            break;
                    }
                }
            }).create().show();
        });
    }
}