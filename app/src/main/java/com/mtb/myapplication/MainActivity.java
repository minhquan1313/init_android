package com.mtb.myapplication;

import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {

    }

    private void bindData() {

        // if (ActivityCompat.checkSelfPermission(this,
        // android.Manifest.permission.BLUETOOTH_CONNECT) !=
        // PackageManager.PERMISSION_GRANTED) {
        // Utils.askPermission(MainActivity.this,
        // android.Manifest.permission.BLUETOOTH_CONNECT, 1);
        // return;
        // }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            return;

        switch (requestCode) {
            case 1:
                break;
        }
    }
}