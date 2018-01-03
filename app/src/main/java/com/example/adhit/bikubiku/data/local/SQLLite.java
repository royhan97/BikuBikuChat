package com.example.adhit.bikubiku.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.adhit.bikubiku.data.model.User;

import java.util.HashMap;

import static android.content.ContentValues.TAG;
import static com.example.adhit.bikubiku.util.Constant.KEY_ALAMAT;
import static com.example.adhit.bikubiku.util.Constant.KEY_APPROVAL_KABIM;
import static com.example.adhit.bikubiku.util.Constant.KEY_BIO;
import static com.example.adhit.bikubiku.util.Constant.KEY_EMAIL;
import static com.example.adhit.bikubiku.util.Constant.KEY_FOTO;
import static com.example.adhit.bikubiku.util.Constant.KEY_ID;
import static com.example.adhit.bikubiku.util.Constant.KEY_ID_HISTORY_USER;
import static com.example.adhit.bikubiku.util.Constant.KEY_ID_LINE;
import static com.example.adhit.bikubiku.util.Constant.KEY_JNS_KEL;
import static com.example.adhit.bikubiku.util.Constant.KEY_KODE_SAKU;
import static com.example.adhit.bikubiku.util.Constant.KEY_NAMA;
import static com.example.adhit.bikubiku.util.Constant.KEY_STATUS_AKUN;
import static com.example.adhit.bikubiku.util.Constant.KEY_STATUS_KABIM;
import static com.example.adhit.bikubiku.util.Constant.KEY_TOKEN;
import static com.example.adhit.bikubiku.util.Constant.KEY_USERNAME;
import static com.example.adhit.bikubiku.util.Constant.KEY_WA;

/**
 * Created by adhit on 03/01/2018.
 */

public class SQLLite extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names

    public SQLLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_USERNAME + " TEXT UNIQUE,"
                + KEY_EMAIL + " TEXT,"
                + KEY_TOKEN + " TEXT,"
                + KEY_KODE_SAKU + " TEXT,"
                + KEY_STATUS_AKUN + " TEXT,"
                + KEY_NAMA + " TEXT,"
                + KEY_WA + " TEXT,"
                + KEY_ID_LINE + " TEXT,"
                + KEY_BIO + " TEXT,"
                + KEY_JNS_KEL + " TEXT,"
                + KEY_ALAMAT + " TEXT,"
                + KEY_FOTO + " TEXT,"
                + KEY_STATUS_KABIM + " TEXT,"
                + KEY_APPROVAL_KABIM + " TEXT,"
                + KEY_ID_HISTORY_USER + " TEXT "+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, user.getId()); // Name
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_TOKEN, user.getToken());
        values.put(KEY_KODE_SAKU, user.getKodeSaku());
        values.put(KEY_STATUS_AKUN, user.getStatusAkun());
        values.put(KEY_NAMA, user.getNama());
        values.put(KEY_WA, user.getWa());
        values.put(KEY_ID_LINE, user.getIdLine());
        values.put(KEY_BIO, user.getBio());
        values.put(KEY_JNS_KEL, user.getJnsKel());
        values.put(KEY_ALAMAT, user.getAlamat());
        values.put(KEY_FOTO, user.getFoto());
        values.put(KEY_STATUS_KABIM, user.getStatusKabim());
        values.put(KEY_APPROVAL_KABIM, user.getApprovalKabim());
        values.put(KEY_ID_HISTORY_USER, user.getIdHistoryUser());
        // Inserting Row

        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put(KEY_ID, cursor.getString(0));
            user.put(KEY_USERNAME, cursor.getString(1));
            user.put(KEY_EMAIL, cursor.getString(2));
            user.put(KEY_TOKEN,cursor.getString(3));
            user.put(KEY_KODE_SAKU,cursor.getString(4));
            user.put(KEY_STATUS_AKUN,cursor.getString(5));
            user.put(KEY_NAMA,cursor.getString(6));
            user.put(KEY_WA,cursor.getString(7));
            user.put(KEY_ID_LINE,cursor.getString(8));
            user.put(KEY_BIO,cursor.getString(9));
            user.put(KEY_JNS_KEL,cursor.getString(10));
            user.put(KEY_ALAMAT,cursor.getString(11));
            user.put(KEY_FOTO,cursor.getString(12));
            user.put(KEY_STATUS_KABIM,cursor.getString(4));
            user.put(KEY_APPROVAL_KABIM,cursor.getString(4));
            user.put(KEY_ID_HISTORY_USER,cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
