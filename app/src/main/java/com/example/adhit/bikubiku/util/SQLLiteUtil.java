package com.example.adhit.bikubiku.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.adhit.bikubiku.BikuBiku;

import static android.content.ContentValues.TAG;
import static com.example.adhit.bikubiku.util.Constant.DATABASE_NAME;
import static com.example.adhit.bikubiku.util.Constant.DATABASE_VERSION;
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
import static com.example.adhit.bikubiku.util.Constant.TABLE_USER;

/**
 * Created by adhit on 03/01/2018.
 */

public class SQLLiteUtil extends SQLiteOpenHelper {
    public SQLLiteUtil() {
        super(BikuBiku.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }
}
