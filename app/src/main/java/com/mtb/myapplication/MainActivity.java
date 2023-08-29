package com.mtb.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ProgressBar progressBar;
    private static final String JSON_URL = "https://thud.fcsevn.com/get-data1";
    List<Tutorial> lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        listView = findViewById(R.id.listView);
        lst = new ArrayList<>();
        progressBar = findViewById(R.id.progessBar);
    }

    private void bindData() {
        loadTutorials();
    }

    private void loadTutorials() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest sReq = new StringRequest(Request.Method.GET, JSON_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray arr = object.getJSONArray("tutorials");
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject tutObj = arr.getJSONObject(i);
                        Tutorial tut = new Tutorial(tutObj.getString("name"), tutObj.getString("imageurl"), tutObj.getString("description"));
                        lst.add(tut);
                    }
                    MyAdapter adapter = new MyAdapter(lst, getApplicationContext());
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    Log.w("Load tutorials: ", "Error: " + e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(sReq);
    }
}