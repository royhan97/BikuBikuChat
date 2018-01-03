package com.example.adhit.bikubiku.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.util.SQLLiteUtil;

import java.util.HashMap;

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

public class SQLLite {

    /**
     * Storing user details in database
     * */
    public static void addUser(User user) {
        SQLiteDatabase db = new SQLLiteUtil().getWritableDatabase();
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
    public static HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = new SQLLiteUtil().getReadableDatabase();
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
    public  static  void deleteUsers() {
        SQLiteDatabase db = new SQLLiteUtil().getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
