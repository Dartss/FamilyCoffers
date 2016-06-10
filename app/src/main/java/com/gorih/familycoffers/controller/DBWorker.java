package com.gorih.familycoffers.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.gorih.familycoffers.model.Expanse;

import java.util.Observable;
import java.util.Observer;

public class DBWorker extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public static DBWorker dbWorker = null;
    private static DbNotifier dbNotifier;

    public static DBWorker getInstance(Context context) {
        if (dbWorker == null) {
            dbWorker = new DBWorker(context);
            dbNotifier = new DbNotifier();
        }
        return dbWorker;
    }

    protected DBWorker(Context context) {
        super(context, "fcdb", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table expanses ("
                + "_id" + " integer primary key autoincrement, " + "category_id int, "
                + "value float, " + "date long" + ");");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    public void addToDB(Expanse expanse){
        ContentValues cv = new ContentValues();
        db = getWritableDatabase();
        cv.put("category_id", expanse.getCategory().getId());
        cv.put("value", expanse.getValue());
        cv.put("date", expanse.getDate());
        db.insert("expanses", null, cv);
        db.close();

        dbNotifier.sendNotifications();
    }

    public void delFromDB(long[] idToDel) {
        db = getWritableDatabase();
        for(long expanseId : idToDel) {
            db.delete("expanses", "_id="+expanseId, null);
        }
        db.close();

        dbNotifier.sendNotifications();
    }

    public void eraseDB(){
        db = getWritableDatabase();
        db.delete("expanses", null, null);
        db.close();

        dbNotifier.sendNotifications();
    }

    public void addObserverTodb(Observer observer) {
        dbNotifier.addObserver(observer);
    }

    private static class DbNotifier extends Observable {
        @Override
        protected void setChanged() {
            super.setChanged();
        }

        protected void sendNotifications() {
            setChanged();
            notifyObservers();
        }
    }
}
