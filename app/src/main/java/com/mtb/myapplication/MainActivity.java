package com.mtb.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Activity context = MainActivity.this;
    private EditText name_input, tel_input, birthday_input;
    private Button insert_btn, update_btn, delete_btn, view_all_btn;
    private UserDB userDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindComponents();

        bindData();
    }

    private void bindComponents() {
        name_input = findViewById(R.id.name_input);
        tel_input = findViewById(R.id.tel_input);
        birthday_input = findViewById(R.id.birthday_input);

        insert_btn = findViewById(R.id.insert_btn);
        update_btn = findViewById(R.id.update_btn);
        delete_btn = findViewById(R.id.delete_btn);
        view_all_btn = findViewById(R.id.view_all_btn);

        userDB = new UserDB(context);

    }

    private void bindData() {
        // if (ActivityCompat.checkSelfPermission(this,
        // android.Manifest.permission.BLUETOOTH_CONNECT) !=
        // PackageManager.PERMISSION_GRANTED) {
        // Utils.askPermission(MainActivity.this,
        // android.Manifest.permission.BLUETOOTH_CONNECT, 1);
        // return;
        // }

        insert_btn.setOnClickListener((View v) -> {
            String birthDay = birthday_input.getText().toString();
            String contact = tel_input.getText().toString();
            String name = name_input.getText().toString();

            boolean isSuccess = userDB.create(name, contact, birthDay);
            Toast.makeText(context, "Thêm " + (isSuccess ? "Thành công" : "Thất bại"), Toast.LENGTH_SHORT).show();
        });
        update_btn.setOnClickListener((View v) -> {
            String birthDay = birthday_input.getText().toString();
            String contact = tel_input.getText().toString();
            String name = name_input.getText().toString();

            boolean isSuccess = userDB.update(name, contact, birthDay);
            Toast.makeText(context, "Cập nhật " + (isSuccess ? "Thành công" : "Thất bại"), Toast.LENGTH_SHORT).show();
        });
        delete_btn.setOnClickListener((View v) -> {
            String name = name_input.getText().toString();

            boolean isSuccess = userDB.delete(name);
            Toast.makeText(context, "Xoá " + (isSuccess ? "Thành công" : "Thất bại"), Toast.LENGTH_SHORT).show();
        });
        view_all_btn.setOnClickListener((View v) -> {
            Cursor cursor = userDB.getAll();
            StringBuilder stringBuilder = new StringBuilder();

            if (cursor.getCount() == 0) {
                stringBuilder.append("Không có dữ liệu.");
            } else
                while (cursor.moveToNext()) {
                    stringBuilder.append("Tên: ").append(cursor.getString(0)).append("\n");
                    stringBuilder.append("SĐT: ").append(cursor.getString(1)).append("\n");
                    stringBuilder.append("Sinh: ").append(cursor.getString(2)).append("\n");
                }

            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setCancelable(true);
            alert.setTitle("Các người dùng");
            alert.setMessage(stringBuilder.toString());
            alert.show();

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED)
            return;

        switch (requestCode) {
            case 1:
                break;
        }
    }

    /**
     * Clear focus when tapping outside of any EditText
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}