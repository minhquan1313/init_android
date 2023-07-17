package com.mtb.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "StudentManagement";
    public static final String TABLE_NAME = "students";
    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_EMAIL = "email";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStatement = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_EMAIL + " TEXT," +
                KEY_PHONE + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(createStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addStudent(Student s) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, s.getName());
        values.put(KEY_EMAIL, s.getEmail());
        values.put(KEY_PHONE, s.getPhone());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Student getStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.query(TABLE_NAME,
                new String[]{
                        KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PHONE
                },
                KEY_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);

        if (c != null) {
            c.moveToFirst();
        }

        Student s = new Student(c.getString(0), c.getString(1), c.getString(2), c.getString(3));

        db.close();

        return null;
    }


    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();

        String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(SELECT_QUERY, null);
        if (c.moveToFirst()) {
            do {

                Student student = new Student();

                student.setId(c.getString(0));
                student.setName(c.getString(1));
                student.setPhone(c.getString(2));
                student.setEmail(c.getString(3));
                students.add(student);

            } while (c.moveToNext());
        }
        return students;
    }
}
