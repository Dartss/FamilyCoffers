package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gorih.familycoffers.R;

public class dlgAddCategory extends DialogFragment implements View.OnClickListener {
    private EditText newCategoryName;
    OnNewCategoryAddedListener mListener;

    public interface OnNewCategoryAddedListener {
        public void onNewCategoryAdded(String newCategoryName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.dlg_new_category_tittle);
        View v = inflater.inflate(R.layout.dialog_new_category, null);
        v.findViewById(R.id.btn_dlg_tak).setOnClickListener(this);
        newCategoryName = (EditText)v.findViewById(R.id.dlg_new_category_name);
        return v;
    }

        @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnNewCategoryAddedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }

    public void onClick(View v) {
        mListener.onNewCategoryAdded(newCategoryName.getText().toString());
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        newCategoryName.getText().clear();
    }
}
