package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.FilterListener;
import com.gorih.familycoffers.controller.HistoryCursorLoader;
import com.gorih.familycoffers.model.Category;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoryFragment extends AbstractFragment implements LoaderManager.
        LoaderCallbacks<Cursor>{
    private static final int LAYOUT = R.layout.fragment_history;
    public static HistoryFragment historyFragment = null;
    ListView listView;
    SimpleCursorAdapter adapter;
    RadioGroup filterRG;
    Long timeFilterValue = 0L;

    public static HistoryFragment getInstance(Context context) {
        Bundle args = new Bundle();

        historyFragment = new HistoryFragment();
        historyFragment.setArguments(args);
        historyFragment.setContext(context);
        historyFragment.setTittle(context.getString(R.string.tab_item_history));

        return historyFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("--HistoryFR--", "OnCreateView");
        view = inflater.inflate(LAYOUT, container, false);
        listView = (ListView) view.findViewById(R.id.lv_history_view_list);

        String[] from = new String[] { "category", "value" , "date"};
        int[] to = new int[] { R.id.iv_history_item_category, R.id.tv_history_item_value, R.id.tv_history_item_date};

        adapter = new SimpleCursorAdapter(this.context, R.layout.history_list_item, null, from, to, 0);

        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

            public boolean setViewValue(View aView, Cursor aCursor, int aColumnIndex) {

                if (aColumnIndex == 3) {
                    Date date = new Date(aCursor.getLong(3));
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                    String dateText = sdf.format(date);
                    TextView textView = (TextView) aView;
                    textView.setText(dateText);
                    return true;
                }

                if (aColumnIndex == 2) {
                    String value = String.format("%.2f", aCursor.getFloat(2));
                    TextView textView = (TextView) aView;
                    textView.setText(value);
                    return true;
                }

                return false;
            }
        });

        listView.setAdapter(adapter);

        getLoaderManager().initLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();

        filterRG = (RadioGroup) view.findViewById(R.id.radio_group_history);
        filterRG.setOnCheckedChangeListener(new FilterListener(Constants.HISTORY_FR_ID));

        return view;
    }


    public void onFilterSelected(long timeFilterValue){
        this.timeFilterValue = timeFilterValue;
        getLoaderManager().restartLoader(0, null, historyFragment);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new HistoryCursorLoader(this.context, timeFilterValue);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }

}