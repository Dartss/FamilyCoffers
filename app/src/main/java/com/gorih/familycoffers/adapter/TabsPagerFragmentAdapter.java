package com.gorih.familycoffers.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gorih.familycoffers.fragment.AbstractFragment;
import com.gorih.familycoffers.fragment.ExpansesFragment;
import com.gorih.familycoffers.fragment.HistoryFragment;
import com.gorih.familycoffers.fragment.StatisticsFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {

    private Map<Integer, AbstractFragment> tabs;
    private Context context;

    public TabsPagerFragmentAdapter (Context context, FragmentManager fm) {
        super(fm);
        this.context = context;

        initTabsMap(context);

    }

    private void initTabsMap(Context context) {
        tabs = new HashMap<>();

        tabs.put(0, ExpansesFragment.getInstance(context));
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
