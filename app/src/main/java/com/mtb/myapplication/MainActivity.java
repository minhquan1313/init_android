package com.mtb.myapplication;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    EditText mobileno, message;
    Button sendsms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        mobileno = (EditText) findViewById(R.id.editText1);
        message = (EditText) findViewById(R.id.editText2);
        sendsms = (Button) findViewById(R.id.button1);
    }

    private void bindData() {


        sendsms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    Utils.askPermission(MainActivity.this, android.Manifest.permission.SEND_SMS, 1);
                    Utils.askPermission(MainActivity.this, Manifest.permission.RECEIVE_SMS, 2);
                    return;
                }

                String no = mobileno.getText().toString();
                String msg = message.getText().toString();

                // Getting intent and PendingIntent instance
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                // Get the SmsManager instance and call the sendTextMessage method to send
                // message
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(no, null, msg, pi, null);

                Toast.makeText(getApplicationContext(), "Message Sent successfully!",
                        Toast.LENGTH_LONG).show();
            }
        });

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