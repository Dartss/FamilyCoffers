package com.gorih.familycoffers.presenter.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.EdgeEffectCompat;
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

public class dlgEditCategory extends DialogFragment implements View.OnClickListener {
    private Category category;
    private CategoryEditedListener listener;
    private EditText newNameField;
    LobsterPicker lobsterPicker;


    public interface CategoryEditedListener {
        void onCategoryChanged(String categoryNewName, int categoryNewColor, int targetCategoryId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (CategoryEditedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CategoryEditedListener");
        }

    }

    public static dlgEditCategory newInstance(int categoryId) {
        dlgEditCategory instance = new dlgEditCategory();

        Bundle args = new Bundle();
        args.putInt("CategoryId", categoryId);
        instance.setArguments(args);

        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        category = Categories.instance.findCategoryById(getArguments().getInt("CategoryId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Edit category");
        View v = inflater.inflate(R.layout.dialog_add_category, null);

        Button apply = (Button) v.findViewById(R.id.btn_dlg_add_category_positive);
        apply.setOnClickListener(this);

        Button dismiss = (Button) v.findViewById(R.id.btn_dlg_add_category_negative);
        dismiss.setOnClickListener(this);

        newNameField = (EditText) v.findViewById(R.id.et_dlg_add_category);
        newNameField.setText(category.getName());

        lobsterPicker = (LobsterPicker) v.findViewById(R.id.lobsterpicker);
        LobsterShadeSlider shadeSlider = (LobsterShadeSlider) v.findViewById(R.id.shadeslider);

        lobsterPicker.addDecorator(shadeSlider);

        lobsterPicker.setColorHistoryEnabled(true);
        lobsterPicker.setHistory(category.getColor());

        return v;
    }

    @Override
    public void onClick(View v) {
        String inputValue = (newNameField.getText().toString());

        if(v.getId() == R.id.btn_dlg_add_category_negative) {
            dismiss();
            return;
        }

        if (isValid(inputValue)){
            listener.onCategoryChanged(newNameField.getText().toString(), lobsterPicker.getColor(), category.getId());
            dismiss();
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        newNameField.getText().clear();
    }

    boolean isValid(String newCategoryName){
        return !newCategoryName.equals("");
    }

}
