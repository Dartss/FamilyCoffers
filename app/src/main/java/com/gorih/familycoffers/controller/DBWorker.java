package com.gorih.familycoffers.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.gorih.familycoffers.model.Expanse;

import java.util.ArrayList;

public class DBWorker extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private final String LOG = "DBWorker";
    private static DBWorker dbWorker = null;

    public static DBWorker getInstance(Context context) {
        if (dbWorker == null) {
            dbWorker = new DBWorker(context);
        }
        return dbWorker;
    }

    protected DBWorker(Context context) {
        super(context, "fcdb", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table expanses ("
                + "_id" + " integer primary key autoincrement, " + "category text, "
                + "value float, " + "date long" + ");");

        Log.d(LOG, "DB Was Created");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void addToDB(Expanse expanse){
        ContentValues cv = new ContentValues();
        db = getWritableDatabase();
        cv.put("category", expanse.getCategory());
        cv.put("value", expanse.getValue());
        cv.put("date", expanse.getDate());
        long rowID = db.insert("expanses", null, cv);

        Log.d(LOG, "row inserted, ID = " + rowID);
        Log.d(LOG, "Named " + expanse.getCategory());
        Log.d(LOG, "Sum " + expanse.getValue());
        Log.d(LOG, "Date " + expanse.getDate());

        db.close();
    }

    public void delFromDB(ArrayList<String> namesAR){
        db = getWritableDatabase();
        for(String nameToDel : namesAR) {
            int delCount = db.delete("expanses", "category = '" + nameToDel + "'", null);
            Log.d(LOG, "DELETED FROM DB " + delCount);
        }
        db.close();
    }

    public void eraseDB(){
        db = getWritableDatabase();
        db.delete("expanses", null, null);
        db.close();
    }

}
