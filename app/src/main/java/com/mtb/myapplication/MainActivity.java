package com.mtb.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("INSERT DATA HERE", "Students data...");
        db.addStudent(new Student("1", "John", "john@gmail.com", "0123123123"));
        db.addStudent(new Student("2", "John2", "john2@gmail.com", "0123123123"));
        db.addStudent(new Student("3", "John3", "john3@gmail.com", "0123123123"));

        List<Student> list = db.getAllStudents();
        for (Student s : list) {
            String log = "Id: " + s.getId() +
                    ", Name: " + s.getName() +
                    ", Phone: " + s.getPhone() +
                    ", Email: " + s.getEmail();

            Log.d("STUDENT", log);
        }
    }
}