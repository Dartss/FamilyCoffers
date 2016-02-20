package com.gorih.familycoffers.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gorih.familycoffers.R;

public class ExpansesFragment extends AbstractFragment {
    private static final int LAYOUT = R.layout.fragment_example;

    public static ExpansesFragment getInstance(Context context) {
        Bundle args = new Bundle();

        ExpansesFragment fragment = new ExpansesFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTittle(context.getString(R.string.tab_item_expanses));

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }
}
