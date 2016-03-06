package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gorih.familycoffers.R;

public class StatisticsFragment extends AbstractFragment {
    private static final int LAYOUT = R.layout.fragment_statistics;

    public static StatisticsFragment getInstance(Context context) {
        Bundle args = new Bundle();

        StatisticsFragment fragment = new StatisticsFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTittle(context.getString(R.string.tab_item_statistics));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }

}
