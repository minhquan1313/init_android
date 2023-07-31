package com.mtb.myapplication;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Context context;
    Button start_btn,
            stop_btn;

    MediaRecorder recorder;
    static final String TAG = "MediaRecording";
    File audioFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        initial();

        bindData();
    }


    private void initial() {
        start_btn = findViewById(R.id.start_btn);
        stop_btn = findViewById(R.id.stop_btn);
        stop_btn.setEnabled(false);

        checkPermissions();
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

    private boolean checkPermissions() {
        int first = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        int second = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return first == PackageManager.PERMISSION_GRANTED && second == PackageManager.PERMISSION_GRANTED;
    }

    public void startRecording() throws IOException {
        start_btn.setEnabled(false);
        stop_btn.setEnabled(true);

        File dir = Environment.getExternalStorageDirectory();
        File x = context.getFilesDir();
        try {
            audioFile = File.createTempFile("sound", ".3gp", x);
        } catch (IOException e) {
            Log.e(TAG, "External storage access error");
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

        Toast.makeText(context, "Added file [" + newUri + "] successfully", Toast.LENGTH_SHORT).show();
    }
}