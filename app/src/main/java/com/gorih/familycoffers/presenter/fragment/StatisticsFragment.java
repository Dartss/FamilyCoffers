package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.controller.PieAsyncLoader;
import com.gorih.familycoffers.controller.PieDrawer;

import java.util.HashMap;

public class StatisticsFragment extends AbstractFragment implements LoaderCallbacks
        <HashMap<String, Float>> {
    private static final int LAYOUT = R.layout.fragment_statistics;
    private static final int DEFAULT_ID = 1;
    RelativeLayout relativeLayout;
    public static StatisticsFragment statisticsFragment = null;

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
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_fragment_statistics);
        getLoaderManager().initLoader(DEFAULT_ID, null, this);
        getLoaderManager().getLoader(DEFAULT_ID).forceLoad();
        return view;
    }

    @Override
    public android.support.v4.content.Loader<HashMap<String, Float>> onCreateLoader(int id, Bundle args) {
        return new PieAsyncLoader(this.context, null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<HashMap<String, Float>> loader,
                               HashMap<String, Float> result) {

        View pieView;

        if (result.size() != 0) {
            pieView = new PieDrawer(this.context, result);
        } else {
            LayoutInflater ltInflater = getLayoutInflater(null);
            pieView = ltInflater.inflate(R.layout.empty_statistics_view, null, false);
        }

        relativeLayout.removeAllViews();
        relativeLayout.addView(pieView);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<HashMap<String, Float>> loader) { }
}
