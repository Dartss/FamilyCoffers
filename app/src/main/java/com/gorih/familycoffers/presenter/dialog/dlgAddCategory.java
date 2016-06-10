package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.gorih.familycoffers.R;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Category;
import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;

public class dlgAddCategory extends DialogFragment implements View.OnClickListener {
    EditText nameField;
    LobsterPicker lobsterPicker;

    OnNewCategoryAddedListener mListener;

    public interface OnNewCategoryAddedListener {
        void onNewCategoryAdded(String newCategoryName, int newCategoryColor);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.dlg_new_category_tittle);
        View v = inflater.inflate(R.layout.dialog_add_category, null);

        Button apply = (Button) v.findViewById(R.id.btn_dlg_add_category_positive);
        apply.setOnClickListener(this);

        Button dismiss = (Button) v.findViewById(R.id.btn_dlg_add_category_negative);
        dismiss.setOnClickListener(this);

        nameField = (EditText) v.findViewById(R.id.et_dlg_add_category);

        lobsterPicker = (LobsterPicker) v.findViewById(R.id.lobsterpicker);
        LobsterShadeSlider shadeSlider = (LobsterShadeSlider) v.findViewById(R.id.shadeslider);

        lobsterPicker.addDecorator(shadeSlider);

        lobsterPicker.setColorHistoryEnabled(true);

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

    @Override
    public void onClick(View v) {
        String inputValue = (nameField.getText().toString());

        if(v.getId() == R.id.btn_dlg_add_category_negative) {
            dismiss();
        }

        if (isValid(inputValue)){
            mListener.onNewCategoryAdded(nameField.getText().toString(), lobsterPicker.getColor());
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        nameField.getText().clear();
    }

    boolean isValid(String newCategoryName){
        if (newCategoryName.equals("")) {
           return false;
        }

        for (Category category : Categories.instance.getAllCategoriesList()) {
            if (category.getName().equals(newCategoryName)) return false;
        }

        return true;
    }
}
