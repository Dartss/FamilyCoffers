package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.MainActivity;
import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.DBWorker;
import com.gorih.familycoffers.controller.FilterListener;
import com.gorih.familycoffers.controller.PieAsyncLoader;
import com.gorih.familycoffers.controller.PieDrawer;
import com.gorih.familycoffers.model.Expanse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class StatisticsFragment extends AbstractFragment implements LoaderCallbacks
        <ArrayList<Expanse>>, Observer {
    private static final int LAYOUT = R.layout.fragment_statistics;
    private static final int DEFAULT_ID = 1;
    private static final String LOG = "--StatisticsFR--";
    public static StatisticsFragment statisticsFragment = null;
    private ScrollView frameLayoutPie;
    private LinearLayout linearLayoutContainer;
    private RadioGroup filterRadioGroup;
    private long timeFilterValue = 0;


    public static StatisticsFragment getInstance(Context context) {
        Bundle args = new Bundle();

        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTittle(context.getString(R.string.tab_item_statistics));

        if (statisticsFragment == null) statisticsFragment = fragment;

        return fragment;
    }

    public void refresh() {
        getLoaderManager().restartLoader(DEFAULT_ID, null, statisticsFragment);
        getLoaderManager().getLoader(DEFAULT_ID).forceLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(LOG, "OnCreateView");
        this.view = inflater.inflate(LAYOUT, null, false);

        DBWorker.getInstance(this.context).addObserverTodb(this);
        frameLayoutPie = (ScrollView) view.findViewById(R.id.ll_statistics_root);
        filterRadioGroup = (RadioGroup) inflater.inflate(R.layout.history_radio_group, linearLayoutContainer, false);
        filterRadioGroup.setOnCheckedChangeListener(new FilterListener(Constants.STATISTICS_FR_ID));

        linearLayoutContainer = new LinearLayout(this.context);
        linearLayoutContainer.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutContainer.setLayoutParams(params);

        getLoaderManager().initLoader(DEFAULT_ID, null, this);
        getLoaderManager().getLoader(DEFAULT_ID).forceLoad();

        return view;
    }

    public void onFilterSelected(Long timeFilterValue) {
        this.timeFilterValue = timeFilterValue;
        refresh();
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Expanse>> onCreateLoader(int id, Bundle args) {
        Log.d(LOG, "TimeFilterValue = " + timeFilterValue);
        return new PieAsyncLoader(this.context, timeFilterValue);

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Expanse>> loader,
                               ArrayList<Expanse> allExpenses) {
        Log.d(LOG, "loader restarted");
        frameLayoutPie.removeAllViews();
        linearLayoutContainer.removeAllViews();

        if(allExpenses.size() == 0) {
            LayoutInflater ltInflater = getLayoutInflater(null);
            frameLayoutPie.addView(ltInflater.inflate(R.layout.empty_statistics_view, null, false));
            return;
        }


        HashMap<String, Float> result = calculatePercentForEachExpanse(allExpenses);
        linearLayoutContainer.addView(filterRadioGroup);
        linearLayoutContainer.addView(new PieDrawer(this.context, result));
        frameLayoutPie.addView(linearLayoutContainer);
    }

    private HashMap<String, Float> calculatePercentForEachExpanse(ArrayList<Expanse> allExpenses) {
        HashMap<String, Float> totalValues = new HashMap<>();
        float sumOfAllExpanses = 0;

        for(Expanse eachExpense : allExpenses ) {
            String curExpCategory = eachExpense.getCategory();
            Float curExpValue = eachExpense.getValue();
            sumOfAllExpanses += curExpValue;

            if (totalValues.containsKey(curExpCategory)) {
                totalValues.put(curExpCategory, totalValues.get(curExpCategory) + curExpValue);
            } else {
                totalValues.put(eachExpense.getCategory(), curExpValue);
            }
        }

        for (Map.Entry<String, Float> entry : totalValues.entrySet() ) {
            totalValues.put(entry.getKey(), (entry.getValue() * 360) / sumOfAllExpanses);
        }

        return totalValues;
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Expanse>> loader) { }

    @Override
    public void update(Observable observable, Object data) {
        refresh();
        Log.d(LOG, "updated");
    }
}
