package com.gorih.familycoffers.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Expanse;

import java.util.ArrayList;

public class PieAsyncLoader extends AsyncTaskLoader<ArrayList<Expanse>> {
    private static final String TAG = "---PAL--";
    DBWorker dbWorker;
    Long dateFrom;

    public PieAsyncLoader(Context context, Long dateFrom) {
        super(context);
        dbWorker = DBWorker.getInstance(context);
        this.dateFrom = dateFrom;
    }

    @Override
    public ArrayList<Expanse> loadInBackground() {
        Cursor cursor;
        SQLiteDatabase db = dbWorker.getReadableDatabase();
        ArrayList<Expanse> allExpanses = new ArrayList<>();

        if (dateFrom > 0) {
            Log.d(TAG, "filtered cursor");
            String selection = "date > ?";
            String[] selectionArgs = new String[] { dateFrom.toString() };

            cursor = db.query("expanses", null, selection, selectionArgs, null, null,
                    null);
        } else {
            Log.d(TAG, "default cursor");
            cursor = db.query("expanses", null, null, null, null, null, null);
        }

        if (cursor.moveToFirst()) {
            int nameColIndex= cursor.getColumnIndex("category_id");
            int valueColIndex = cursor.getColumnIndex("value");
            int dateColIndex = cursor.getColumnIndex("date");

            do {
                Integer expanseCategoryId = cursor.getInt(nameColIndex);
                Float expanseValue = cursor.getFloat(valueColIndex);
                Long expanseDate = cursor.getLong(dateColIndex);

                allExpanses.add(new Expanse(expanseValue, expanseDate, expanseCategoryId));
            } while (cursor.moveToNext());

        } else {
            Log.d(TAG, "size is zerro");
        }

//        Log.d(TAG, "\t" + allExpanses);

        return allExpanses;
    }
}
