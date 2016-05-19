package com.gorih.familycoffers.controller;

import android.util.Log;
import android.widget.RadioGroup;

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.R;
import com.gorih.familycoffers.presenter.fragment.HistoryFragment;
import com.gorih.familycoffers.presenter.fragment.StatisticsFragment;

import java.util.Calendar;
//
public class FilterListener implements RadioGroup.OnCheckedChangeListener {
    private Long timeFilterValue = 0L;
    private static final String LOG = "--FilterListener__";
    private int callingFragmentId;

    public FilterListener(int callingFragmentId) {
        this.callingFragmentId = callingFragmentId;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Calendar c = Calendar.getInstance();
        switch (checkedId) {
            case R.id.rb_history_today:
                Log.d(LOG, "Today");
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                timeFilterValue = c.getTimeInMillis();
                break;
            case R.id.rb_history_month:
                Log.d(LOG, "Month");
                c.set(Calendar.DAY_OF_MONTH, 1);
                c.set(Calendar.HOUR_OF_DAY, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                c.set(Calendar.MILLISECOND, 0);
                timeFilterValue = c.getTimeInMillis();
                break;
            case R.id.rb_history_alltime:
                Log.d(LOG, "All Time");
                timeFilterValue = 0L;
                break;
        }

        if (callingFragmentId == Constants.STATISTICS_FR_ID){
            StatisticsFragment.statisticsFragment.onFilterSelected(timeFilterValue);
            return;
        }

        if (callingFragmentId == Constants.HISTORY_FR_ID) {
            HistoryFragment.historyFragment.onFilterSelected(timeFilterValue);
        } else {
            Log.d(LOG, "Wrong Fragment ID");
        }
    }


}