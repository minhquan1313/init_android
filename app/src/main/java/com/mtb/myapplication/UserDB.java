package com.mtb.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDB extends SQLiteOpenHelper {

    private static final String TABLE = "User";

    public UserDB(Context context) {
        super(context, TABLE + ".db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.join(
                " ",
                "" +
                        "CREATE TABLE " + TABLE + "(" +
                        "name TEXT PRIMARY KEY," +
                        "contact TEXT," +
                        "birth_day TEXT" +
                        ")"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
    }

    public boolean create(String name, String contact, String birthDay) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("name", name);
            cv.put("contact", contact);
            cv.put("birth_day", birthDay);

            long result = db.insert(TABLE, null, cv);

            return result != -1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(String name, String contact, String birthDay) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("contact", contact);
            cv.put("birth_day", birthDay);

            Cursor cursor = db.rawQuery(
                    String.join(
                            " ",
                            "" +
                                    "SELECT * FROM " + TABLE +
                                    "WHERE name = ?"),
                    new String[] { name });

            if (cursor.getCount() == 0)
                return false;

            long result = db.update(TABLE, cv, "name = ?", new String[] { name });

            return result != -1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(String name) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery(
                    String.join(
                            " ",
                            "" +
                                    "SELECT * FROM " + TABLE +
                                    "WHERE name = ?"),
                    new String[] { name });

            if (cursor.getCount() == 0)
                return false;

            long result = db.delete(TABLE, "name = ?", new String[] { name });

            return result != -1;
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor getAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                String.join(
                        " ",
                        "" +
                                "SELECT * FROM " + TABLE),
                null);

        return cursor;
    }
}
