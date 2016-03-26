package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Category;

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
        View v = inflater.inflate(R.layout.dialog_add_category, null);
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
                    + " must implement OnNewCategoryAddedListener");
        }

    }

    public void onClick(View v) {
        String inputValue = (newCategoryName.getText().toString());

        if (isValid(inputValue)){
            mListener.onNewCategoryAdded(inputValue);
            dismiss();
        }
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        newCategoryName.getText().clear();
    }

    boolean isValid(String newCategoryName){
        if (newCategoryName.equals("")) {
           return false;
        }
        for (Category category : Categories.getInstance().getAllCategoriesMap().values()) {
            if (category.getName().equals(newCategoryName)) return false;
        }

        return true;
    }
}
