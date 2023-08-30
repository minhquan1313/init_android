package com.mtb.myapplication;

import android.app.Activity;

import androidx.core.app.ActivityCompat;

public class Utils {
    public static void askPermission(Activity activity, String permission, int code) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            activity.requestPermissions(new String[] { permission }, code);
        }
    }

    public static void askPermission(Activity activity, String[] permissions, int code) {
        activity.requestPermissions(permissions, code);
    }
}
