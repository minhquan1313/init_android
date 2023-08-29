package com.mtb.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Activity context;
    Button start_btn,
            stop_btn;
    MediaRecorder recorder;
    File audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        bindComponents();

        bindData();

        permissions();
    }

    private void permissions() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Utils.askPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE, 2);
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Utils.askPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE, 3);
            return;
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            Utils.askPermission(context, Manifest.permission.RECORD_AUDIO, 1);
            return;
        }
    }

    private void bindComponents() {
        start_btn = findViewById(R.id.start_btn);
        stop_btn = findViewById(R.id.stop_btn);
        stop_btn.setEnabled(false);

    }

    private void bindData() {
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    startRecording();
                } catch (IOException e) {
                    return;
                }
            }
        });
        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecording();
            }
        });

    }

    public void startRecording() throws IOException {
        start_btn.setEnabled(false);
        stop_btn.setEnabled(true);

//        File x = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
//        File x = Environment.getExternalStorageDirectory();
        File x = context.getFilesDir();
        File x2 = new File(getFilesDir(), "mtb_ne");
        if (!x.exists()) {
            x.mkdirs();
        }

        try {
            audioFile = File.createTempFile("sound", ".3gp", x);
        } catch (IOException e) {
            Toast.makeText(context, "External storage access error", Toast.LENGTH_SHORT).show();
            return;
        }

        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audioFile.getAbsolutePath());

        recorder.prepare();
        recorder.start();
    }

    public void stopRecording() {
        start_btn.setEnabled(true);
        stop_btn.setEnabled(false);

        recorder.stop();
        recorder.release();

        addRecordedToMediaLib();
    }

    private void addRecordedToMediaLib() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();

        values.put(MediaStore.Audio.Media.TITLE, "audio_" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());

        ContentResolver resolver = getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = resolver.insert(base, values);

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));

        Toast.makeText(context, "Added file [" + newUri + "] successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            return;

        permissions();


        switch (requestCode) {
            case 1:
                break;
        }
    }
}