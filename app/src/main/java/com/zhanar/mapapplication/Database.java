package com.zhanar.mapapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Жанар on 25.05.2016.
 */
public class Database {

    private static final String DB_NAME = "Database";
    private static final int DB_VERSION = 3;

    private static final String TABLE_MAP = "Map";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LATITUDE = "Latitude";
    public static final String COLUMN_LONGITUDE = "Longitude";

    public static final String MAP_TABLE_CREATE = " create table if not exists " + TABLE_MAP
            + " ( " + COLUMN_ID + " integer primary key autoincrement , "
            + COLUMN_TITLE + " text null , "
            + COLUMN_LATITUDE + " float null , "
            + COLUMN_LONGITUDE + " float null );";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;


    public Database(Context ctx) {
        mCtx = ctx;
    }

    // открываем подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрываем подключение
    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    public void saveLocation(String locationName, LatLng latLng){
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TITLE, locationName);
        contentValues.put(COLUMN_LATITUDE, latLng.latitude);
        contentValues.put(COLUMN_LONGITUDE, latLng.longitude);

        mDB.insertWithOnConflict(TABLE_MAP, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<Map> getLocations() {
        List<Map> list = new ArrayList<Map>();

        Cursor cursor = mDB.rawQuery("SELECT * FROM " + TABLE_MAP, null);

        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex(COLUMN_ID);
            int nameColIndex = cursor.getColumnIndex(COLUMN_TITLE);
            int ltColIndex = cursor.getColumnIndex(COLUMN_LATITUDE);
            int lgColIndex = cursor.getColumnIndex(COLUMN_LONGITUDE);

            do {
                Map map = new Map();
                map.setId(cursor.getInt(idColIndex));
                map.setName(cursor.getString(nameColIndex));
                map.setLatitude(cursor.getDouble(ltColIndex));
                map.setLongitude(cursor.getDouble(lgColIndex));

                list.add(map);
            }
            while (cursor.moveToNext());
        } else {
            Log.e("ERROR", "0 rows");
        }
        cursor.close();

        return list;
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Database.MAP_TABLE_CREATE);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAP);
            this.onCreate(db);
        }
    }
}
