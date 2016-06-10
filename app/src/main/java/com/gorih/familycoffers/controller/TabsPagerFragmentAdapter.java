package com.gorih.familycoffers.controller;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gorih.familycoffers.presenter.fragment.AbstractFragment;
import com.gorih.familycoffers.presenter.fragment.CategoriesFragment;
import com.gorih.familycoffers.presenter.fragment.HistoryFragment;
import com.gorih.familycoffers.presenter.fragment.StatisticsFragment;

import java.util.HashMap;
import java.util.Map;


public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    private Map<Integer, AbstractFragment> tabs;

    public TabsPagerFragmentAdapter (Context context, FragmentManager fm) {
        super(fm);
        initTabsMap(context);
    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();

        tabs.put(0, CategoriesFragment.getInstance(context));
        tabs.put(1, StatisticsFragment.getInstance(context));
        tabs.put(2, HistoryFragment.getInstance(context));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (tabs.get(position)).getTittle();
    }

    @Override
    public Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
