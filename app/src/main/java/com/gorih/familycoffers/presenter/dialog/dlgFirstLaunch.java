package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.R;


public class dlgFirstLaunch extends DialogFragment implements View.OnClickListener{

    ModeSelectionListener selectionListener;

    public interface ModeSelectionListener{
        void onModeSelected(int mode);
    }

    public static dlgFirstLaunch newInstance() {
        dlgFirstLaunch f = new dlgFirstLaunch();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            selectionListener = (ModeSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement ModeSelectionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_first_launch, null);

        v.findViewById(R.id.btn_first_launch_offline).setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_first_launch_offline){
            selectionListener.onModeSelected(Constants.OFFLINE_MODE);
        } else {
            selectionListener.onModeSelected(Constants.ONLINE_MODE);
        }

        dismiss();
    }
}
