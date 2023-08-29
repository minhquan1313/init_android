package com.mtb.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView hello_text;
    String[] items = {"D0", "D1", "D2", "D3", "D4", "D5", "D6"};
    private TextView txtMsg;
    private GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        txtMsg = findViewById(R.id.txtMsg);
        grid = findViewById(R.id.grid);


    }

    private void bindData() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener((c, v, p, i) -> {
            txtMsg.setText(items[p]);
        });
    }
}