package com.mtb.myapplication;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] itemsArray = new String[]{
            "Kỹ thuật lập trình",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính",
            "Cơ sở dữ liệu",
            "Lập trình hướng đối tượng",
            "Mạng máy tính"
    };
    List<String> items;
    ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        listView = findViewById(R.id.listView1);
    }

    private void bindData() {
        items = new ArrayList<>();
        items.addAll(Arrays.asList(itemsArray));

        adapter = new ArrayAdapter<>(this, R.layout.my_list_view, R.id.textViewLV, items);
        listView.setAdapter(adapter);
    }

    // When hold an item and the floating appear
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        String title = items.get(info.position); // items[info.position];
        menu.setHeaderTitle(title);
    }

    // When select a
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int id = item.getItemId();
        if (id == R.id.menuItemDelete) {
            items.remove(info.position);
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onContextItemSelected(item);
    }
}