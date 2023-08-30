package com.mtb.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    Activity activity = MainActivity.this;
    Context context = MainActivity.this;
    String file = "mtbFile";
    TextView txt_data1;
    EditText input_txt1;
    Button btn_save1, btn_load1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        // hello_text = findViewById(R.id.hello_text);
        txt_data1 = findViewById(R.id.txt_data1);
        input_txt1 = findViewById(R.id.input_txt1);
        btn_save1 = findViewById(R.id.btn_save1);
        btn_load1 = findViewById(R.id.btn_load1);
    }

    private void bindData() {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            Utils.askPermission(activity, Manifest.permission.INTERNET, 1);
        }

        btn_save1.setOnClickListener(view -> {
            String data = input_txt1.getText().toString();
            try {
                FileOutputStream outputStream = openFileOutput(file, MODE_APPEND);
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        btn_load1.setOnClickListener(view -> {
            try {
                FileInputStream inputStream = openFileInput(file);

                int c;
                StringBuilder temp = new StringBuilder();

                while ((c = inputStream.read()) != -1) {
                    temp.append((char) c);
                }

                inputStream.close();
                txt_data1.setText(temp.toString());

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            return;

        switch (requestCode) {
            case 1:
                break;
        }
    }

    /**
     * Clear focus when tapping outside of any EditText
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();

            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}