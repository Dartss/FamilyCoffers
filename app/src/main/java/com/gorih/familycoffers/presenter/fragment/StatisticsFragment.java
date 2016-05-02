package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.PieAsyncLoader;
import com.gorih.familycoffers.controller.PieDrawer;
import com.gorih.familycoffers.model.Expanse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatisticsFragment extends AbstractFragment implements LoaderCallbacks
        <ArrayList<Expanse>> {
    private static final int LAYOUT = R.layout.fragment_statistics;
    private static final int DEFAULT_ID = 1;
    public static StatisticsFragment statisticsFragment = null;
    private ScrollView frameLayoutPie;


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
        getLoaderManager().getLoader(DEFAULT_ID).forceLoad();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(LAYOUT, null, false);

        frameLayoutPie = (ScrollView) view.findViewById(R.id.ll_statistics_root);

        getLoaderManager().initLoader(DEFAULT_ID, null, this);
        getLoaderManager().getLoader(DEFAULT_ID).forceLoad();

        return view;
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<Expanse>> onCreateLoader(int id, Bundle args) {
        return new PieAsyncLoader(this.context, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<Expanse>> loader,
                               ArrayList<Expanse> allExpenses) {

        frameLayoutPie.removeAllViews();

        if(allExpenses.size() == 0) {
            LayoutInflater ltInflater = getLayoutInflater(null);
            frameLayoutPie.addView(ltInflater.inflate(R.layout.empty_statistics_view, null, false));
            return;
        }

        HashMap<String, Float> result = calculatePercentForEachExpanse(allExpenses);

        frameLayoutPie.addView(new PieDrawer(this.context, result));
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
}
