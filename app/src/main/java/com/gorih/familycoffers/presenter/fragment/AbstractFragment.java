package com.gorih.familycoffers.presenter.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import java.lang.reflect.Field;

public class AbstractFragment extends Fragment {
    protected View view;
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

    @Override
    public void onDetach() {
        super.onDetach();

        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
