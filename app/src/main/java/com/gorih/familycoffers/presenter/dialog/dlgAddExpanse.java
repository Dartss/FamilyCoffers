package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gorih.familycoffers.R;


public class dlgAddExpanse extends DialogFragment implements View.OnClickListener {
    private EditText newExpanseValue;
    private String categoryName;
    private final float MAX_VALUE = 1000000;

    OnNewExpanseAddedListener mListener;

    public interface OnNewExpanseAddedListener {
        public void onNewExpanseAdded(float newExpanseValue, long date, String category);
    }

    public static dlgAddExpanse newInstance(String categoryName) {
        dlgAddExpanse f = new dlgAddExpanse();

        Bundle args = new Bundle();
        args.putString("CategoryName", categoryName);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryName = getArguments().getString("CategoryName");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(categoryName);

        View v = inflater.inflate(R.layout.dialog_add_expanse, null);

        TextView tvExpanseCategory = (TextView) v.findViewById(R.id.tv_dlg_expanse_category_name);
        tvExpanseCategory.setText(R.string.dlg_new_expanse_tittle);

        v.findViewById(R.id.button_dlg_expanse_confirm).setOnClickListener(this);

        newExpanseValue = (EditText)v.findViewById(R.id.et_dlg_expanse_value);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnNewExpanseAddedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewExpanseAddedListener");
        }

    }

    @Override
    public void onClick(View v) {
        float value = 0;
        try{
            value = Float.valueOf(newExpanseValue.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (isValid(value)) {
            mListener.onNewExpanseAdded(value, System.currentTimeMillis(), categoryName);
            dismiss();
        }
    }

    private boolean isValid(float value) {
        return !(value <= 0 || value > MAX_VALUE);
    }
}
