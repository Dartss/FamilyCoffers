package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.R;

public class dlgSelectLanguage extends DialogFragment{
    private static final String TAG = "--dlgSelectLanguage--";
    LanguageSelectionListener listener;
    RadioGroup rgLanguageSelection;
    private String chosenLocal;

    public interface LanguageSelectionListener{
        void onLanguageSelected(String  langId);
    }

    public static dlgSelectLanguage newInstance() {
        dlgSelectLanguage f = new dlgSelectLanguage();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (LanguageSelectionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement LanguageSelectionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_select_language, null);

        getDialog().setTitle(R.string.title_dlg_select_language);

        rgLanguageSelection = (RadioGroup) v.findViewById(R.id.rg_dlg_select_language);

        rgLanguageSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged"+checkedId);
                if (checkedId == R.id.rb_english_dlg_select_language) {
                    chosenLocal = "eng";
                } else if (checkedId == R.id.rb_ukrainian_dlg_select_language) {
                    chosenLocal = "ukr";
                } else {
                    chosenLocal = "ru";
                }
            }
        });
        Button btnOk = (Button) v.findViewById(R.id.btn_ok_dlg_select_language);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLanguageSelected(chosenLocal);
                dismiss();
            }
        });

        return v;
    }

}
