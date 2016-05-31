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

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.DBWorker;
import com.gorih.familycoffers.controller.FilterListener;
import com.gorih.familycoffers.controller.PieAsyncLoader;
import com.gorih.familycoffers.controller.PieDrawer;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Expanse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class StatisticsFragment extends AbstractFragment implements LoaderCallbacks
        <ArrayList<Expanse>>, Observer {
    private static final int LAYOUT = R.layout.fragment_statistics;
    private static final int DEFAULT_ID = 1;
    private static final String TAG = "--StatisticsFR--";
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
        Log.d(TAG, "OnCreateView");
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
        Log.d(TAG, "onFilterSelected");
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Expanse>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        return new PieAsyncLoader(this.context, timeFilterValue);

    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Expanse>> loader,
                               ArrayList<Expanse> allExpenses) {
        linearLayoutContainer.removeAllViews();
        frameLayoutPie.removeAllViews();

        if(allExpenses.size() == 0) {
            Log.d(TAG, "Nothing to show3");
            LayoutInflater ltInflater = getLayoutInflater(null);
            linearLayoutContainer.addView(filterRadioGroup);
            linearLayoutContainer.addView(ltInflater.inflate(R.layout.empty_statistics_view, frameLayoutPie, false));
            frameLayoutPie.addView(linearLayoutContainer);
            return;
        }

        HashMap<Integer, Float> result = calculatePercentForEachExpanse(allExpenses);
        linearLayoutContainer.addView(filterRadioGroup);
        linearLayoutContainer.addView(new PieDrawer(this.context, result));
        frameLayoutPie.addView(linearLayoutContainer);
    }

    private HashMap<Integer, Float> calculatePercentForEachExpanse(ArrayList<Expanse> allExpenses) {
        HashMap<Integer, Float> totalValues = new HashMap<>();
        float sumOfAllExpanses = 0;

        for(Expanse eachExpense : allExpenses ) {
            int curExpCategory = eachExpense.getCategory().getId();
            Float curExpValue = eachExpense.getValue();
            sumOfAllExpanses += curExpValue;

            if (totalValues.containsKey(curExpCategory)) {
                totalValues.put(curExpCategory, totalValues.get(curExpCategory) + curExpValue);
            } else {
                totalValues.put(curExpCategory, curExpValue);
            }
        }

        for (Map.Entry<Integer, Float> entry : totalValues.entrySet() ) {
            totalValues.put(entry.getKey(), (entry.getValue() * 360) / sumOfAllExpanses);
        }

        return totalValues;
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<Expanse>> loader) { }

    @Override
    public void update(Observable observable, Object data) {
        refresh();
        Log.d(TAG, "updated");
    }
}
