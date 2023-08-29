package com.mtb.myapplication;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }


    private void bindComponents() {
        textView1 = (TextView) findViewById(R.id.textView1);

    }

    private void bindData() {

        // if (ActivityCompat.checkSelfPermission(this,
        // android.Manifest.permission.BLUETOOTH_CONNECT) !=
        // PackageManager.PERMISSION_GRANTED) {
        // Utils.askPermission(MainActivity.this,
        // android.Manifest.permission.BLUETOOTH_CONNECT, 1);
        // return;
        // }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Utils.askPermission(MainActivity.this, android.Manifest.permission.READ_PHONE_STATE, 1);
            return;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_BASIC_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Utils.askPermission(MainActivity.this, android.Manifest.permission.READ_BASIC_PHONE_STATE, 2);
            return;
        }

        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        // Calling the methods of TelephonyManager the returns the information
        String IMEINumber = tm.getDeviceId();
        String subscriberID = tm.getDeviceId();
        String SIMSerialNumber = tm.getSimSerialNumber();
        String networkCountryISO = tm.getNetworkCountryIso();
        String SIMCountryISO = tm.getSimCountryIso();

        String softwareVersion = tm.getDeviceSoftwareVersion();

        String voiceMailNumber = tm.getVoiceMailNumber();

        // Get the phone type
        String strphoneType = "";

        int phoneType = tm.getPhoneType();

        switch (phoneType) {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType = "CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType = "GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType = "NONE";
                break;
        }

        // getting information if phone is in roaming
        boolean isRoaming = tm.isNetworkRoaming();

        String info = "Phone Details:\n";
        info += "\n IMEI Number:" + IMEINumber;
        info += "\n SubscriberID:" + subscriberID;
        info += "\n Sim Serial Number:" + SIMSerialNumber;
        info += "\n Network Country ISO:" + networkCountryISO;
        info += "\n SIM Country ISO:" + SIMCountryISO;
        info += "\n Software Version:" + softwareVersion;
        info += "\n Voice Mail Number:" + voiceMailNumber;
        info += "\n Phone Network Type:" + strphoneType;
        info += "\n In Roaming? :" + isRoaming;

        textView1.setText(info);// displaying the information in the textView
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