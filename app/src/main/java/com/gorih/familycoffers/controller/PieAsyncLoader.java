package com.gorih.familycoffers.controller;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class PieAsyncLoader extends AsyncTaskLoader<HashMap<String, Float>> {
    DBWorker dbWorker;
    Long dateFrom;

    public PieAsyncLoader(Context context, Long dateFrom) {
        super(context);
        dbWorker = DBWorker.getInstance(context);
        this.dateFrom = dateFrom;
    }

    @Override
    public HashMap<String, Float> loadInBackground() {
        Cursor cursor;
        SQLiteDatabase db = dbWorker.getReadableDatabase();
        HashMap<String, Float> cutedPie = new HashMap<>();


        if (dateFrom != null ) {
            String selection = "date > ?";
            String[] selectionArgs = new String[] { dateFrom.toString() };

            cursor = db.query("expanses", null, selection, selectionArgs, null, null,
                    null);
        } else {
            Log.d("---CSM--", "default cursor");
            cursor = db.query("expanses", null, null, null, null, null, null);
        }

        long wastedMoney = 0;

        //если список трат не пуст
        if (cursor.moveToFirst()) {

            // определить номера столбцов по имени в выборке
            int nameColIndex= cursor.getColumnIndex("category");
            int valueColIndex = cursor.getColumnIndex("value");
            do {
                //определить общую сумму каждой траты
                String expanseCategory = cursor.getString(nameColIndex);
                Float expanseValue = cursor.getFloat(valueColIndex);

                wastedMoney += expanseValue;
                if(cutedPie.containsKey(expanseCategory)){
                    cutedPie.put(expanseCategory, cutedPie.get(expanseCategory) + expanseValue);
                }else{
                    cutedPie.put(expanseCategory, expanseValue);
                }

            } while (cursor.moveToNext());

            for (Map.Entry<String, Float> entry : cutedPie.entrySet() ) {
                cutedPie.put(entry.getKey(), (entry.getValue() * 360) / wastedMoney);
            }

        } else {
            Log.d("---Log---", "size is zerro");
        }

        Log.d("---Log---", "\t" + cutedPie);
        cursor.close();
        db.close();

        return cutedPie;
    }
}
