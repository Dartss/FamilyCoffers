package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.DBWorker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryFragment extends AbstractFragment implements LoaderManager.
        LoaderCallbacks<Cursor>{
    private static final int LAYOUT = R.layout.fragment_history;
    ListView listView;
    SimpleCursorAdapter adapter;

    public static HistoryFragment getInstance(Context context) {
        Bundle args = new Bundle();

        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTittle(context.getString(R.string.tab_item_history));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);
        listView = (ListView) view.findViewById(R.id.lv_history_view_list);

        String[] from = new String[] { "category", "value" , "date"};
        int[] to = new int[] { R.id.iv_history_item_icon, R.id.tv_history_item_value, R.id.tv_history_item_date};

        adapter = new SimpleCursorAdapter(this.context, R.layout.history_list_item, null, from, to, 0);

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {

                if (aColumnIndex == 3) {
                    Date date = new Date(aCursor.getLong(3));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    String dateText = sdf.format(date);
                    TextView textView = (TextView) aView;
                    textView.setText("Date: " + dateText);
                    return true;
                }

                if (aColumnIndex == 2) {
                    String value = String.format("%.2f", aCursor.getFloat(2));
                    TextView textView = (TextView) aView;
                    textView.setText("Value: " + value);
                    return true;
                }

                return false;
            }
        });

        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
        return view;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new MyCursorLoader(this.context);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    static class MyCursorLoader extends CursorLoader {

        public MyCursorLoader(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            return DBWorker.getInstance(getContext()).getReadableDatabase().query("expanses",null, null, null, null, null, null);
        }

    }
}