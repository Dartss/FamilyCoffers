package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Category;
import com.gorih.familycoffers.model.Expanse;


public class dlgAddExpanse extends DialogFragment implements View.OnClickListener {
    private EditText newExpanseValue;
    private Category category;
    private final float MAX_VALUE = 1000000;

    OnNewExpanseAddedListener mListener;


    public interface OnNewExpanseAddedListener {
        void onNewExpanseAdded(Expanse newExpanse);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, 0);
        super.onCreate(savedInstanceState);

        category = Categories.instance.findCategoryById(getArguments().getInt("CategoryId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dlg_add_expanse, null);

        TextView tvExpanseCategory = (TextView) v.findViewById(R.id.tv_dlg_expanse_category_name);
        tvExpanseCategory.setText(category.getName());

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
            Expanse newExpanse = new Expanse(value, System.currentTimeMillis(), category.getId());
            mListener.onNewExpanseAdded(newExpanse);
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        newExpanseValue.setText("");
    }

    private boolean isValid(float value) {
        return !(value <= 0 || value > MAX_VALUE);
    }

}
