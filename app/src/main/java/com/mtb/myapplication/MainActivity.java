package com.mtb.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView txtMsg;
    AutoCompleteTextView txtAutoComplete;
    String[] items = {"words", "starting", "with", "set", "Setback", "Setline", "Setoffs", "Setouts", "Setters", "Setting", "Settled", "Settler", "Wordless", "Wordiness", "Adios"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        txtMsg = findViewById(R.id.txtMsg);
        txtAutoComplete = findViewById(R.id.autoComplete1);

    }

    private void bindData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, items);
        txtAutoComplete.setAdapter(adapter);
        txtAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                txtMsg.setText(txtAutoComplete.getText());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}