package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public class AbstractFragment extends Fragment {
    protected View view;
    private static final Field sChildFragmentManagerField;
    private static final String LOGTAG = "FragmentLOG";


    protected Context context;

    private String tittle;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;

    }

    public String getTittle() {
        return tittle;
    }

    static {
        Field f = null;
        try {
            f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.e(LOGTAG, "Error getting mChildFragmentManager field", e);
        }
        sChildFragmentManagerField = f;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (sChildFragmentManagerField != null) {
            try {
                sChildFragmentManagerField.set(this, null);
            } catch (Exception e) {
                Log.e(LOGTAG, "Error setting mChildFragmentManager field", e);
            }
        }
    }

}
