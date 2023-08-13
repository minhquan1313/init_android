package com.mtb.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {
    TextView get_btn,
            lat_text,
            long_text;

    Context context;
    LocationRequest locationRequest;

    FusedLocationProviderClient fusedLocationProviderClient;

    final int ACCESS_FINE_LOCATION_CODE = 1;
    final int ACCESS_FINE_LOCATION_AND_INTERNET_CODE = 4;
    final int INTERNET_CODE = 3;
    final int REQUEST_CHECK_SETTINGS_CODE = 2;
    boolean internetPermission = false;
    boolean fineLocationPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        initial();
        btnCLick();
    }

    private void initial() {
        get_btn = findViewById(R.id.get_btn);
        lat_text = findViewById(R.id.lat_text);
        long_text = findViewById(R.id.long_text);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(1000);
    }

    private void btnCLick() {
        get_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                internetPermission = isGrantedPermission(Manifest.permission.INTERNET);
                fineLocationPermission = (isGrantedPermission(Manifest.permission.ACCESS_FINE_LOCATION) ||
                        isGrantedPermission(Manifest.permission.ACCESS_COARSE_LOCATION));

                boolean permissionGranted = fineLocationPermission && internetPermission;

                if (permissionGranted) {
                    getLocationHandler();
                } else {
                    askPermission(Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION_CODE);
                    askPermission(Manifest.permission.INTERNET, INTERNET_CODE);
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocationHandler() {
        if (fineLocationPermission && internetPermission)
            if (isGPSEnabled()) {
                lat_text.setText("...");
                long_text.setText("...");
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        // Stop getting location
                        fusedLocationProviderClient.removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                            int index = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(index).getLatitude();
                            double longitude = locationResult.getLocations().get(index).getLongitude();

                            lat_text.setText(String.valueOf(latitude));
                            long_text.setText(String.valueOf(longitude));
                        }
                    }
                }, Looper.getMainLooper());
            } else {
                turnOnGPS();
            }
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        return isEnabled;
    }

    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(MainActivity.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(MainActivity.this,
                                        REQUEST_CHECK_SETTINGS_CODE);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Device does not have location
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLocationHandler();
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                }
                break;
        }

    }

    private boolean isGrantedPermission(String permission) {
        return (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED);
    }

    private void askPermission(String permission, int code) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permission)) {
            requestPermissions(new String[] { permission }, code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0)
            return;

        switch (requestCode) {
            case ACCESS_FINE_LOCATION_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fineLocationPermission = true;

                    getLocationHandler();
                } else {
                    fineLocationPermission = false;
                }
                break;

            case INTERNET_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    internetPermission = true;

                    getLocationHandler();
                } else {
                    internetPermission = false;
                }
                break;
        }
    }

}
