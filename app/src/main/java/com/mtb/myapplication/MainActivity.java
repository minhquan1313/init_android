package com.mtb.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Activity context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        // hello_text = findViewById(R.id.hello_text);

    }

    private void bindData() {
        // if (ActivityCompat.checkSelfPermission(context,
        // Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
        // Utils.askPermission(context, Manifest.permission.BLUETOOTH, 1);
        // return;
        // }

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