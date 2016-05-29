package com.gorih.familycoffers.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.CursorLoader;
import android.util.Log;

public class HistoryCursorLoader extends CursorLoader {

    private String dateFilter = null;

    public HistoryCursorLoader(Context context, long filter) {
        super(context);
        try {
            this.dateFilter = String.valueOf(filter);
        } catch (Exception e) {
            Log.d("HISTORY", "date filter failure");
        }
    }

    @Override
    public Cursor loadInBackground() {
        SQLiteDatabase db = DBWorker.dbWorker.getReadableDatabase();
        String selection = "date > ?";
        String[] selectionArgs = new String[] { dateFilter };

        Cursor c = db.query("expanses", null, selection, selectionArgs, null, null, null);

        return c;
    }
}
