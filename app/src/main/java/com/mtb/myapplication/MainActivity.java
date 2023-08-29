package com.mtb.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        edtMsg = findViewById(R.id.edtMsg);
    }

    private void bindData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setTitle("Hé lô mấy cưng");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            edtMsg.setText("Search...");
            return true;
        } else if (id == R.id.action_share) {
            edtMsg.setText("Share...");
            return true;
        } else if (id == R.id.action_download) {
            edtMsg.setText("Download...");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}