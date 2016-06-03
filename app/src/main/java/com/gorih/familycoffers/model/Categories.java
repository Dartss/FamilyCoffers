package com.gorih.familycoffers.model;

import android.content.Context;
import android.util.Log;

import com.gorih.familycoffers.Constants;
import com.gorih.familycoffers.controller.FileWorker;

import java.util.ArrayList;

public class Categories {
    private static final String TAG = "--Categories--";
    private ArrayList<Category> allCategories = new ArrayList<>();
    public static Categories instance = null;
    private Context context;

    public static Categories initiation(Context context) {
        if (instance == null) {
            instance = new Categories(context);
        }
        return instance;
    }

    private Categories(Context context) {
        this.context = context;
        init(); }

    public void init() {
        allCategories = FileWorker.getInstance(context).readCategories();
    }

    public ArrayList<Category> getAllCategoriesList() {
        return allCategories;
    }

    public void addNewCategory(String newCategoryName, int newCategoryColor) {
        Category newCategory = new Category(newCategoryName, newCategoryColor,
                Constants.DEFAULT_CATEGORY_ICON, allCategories.size());
        allCategories.add(newCategory);
        FileWorker.getInstance(context).rewriteCategoriesList();
        Log.d(TAG, "New category added:"+newCategoryName+" id= "+newCategory.getId());
    }

    public Category findCategoryById(int id) {
        for(Category category : allCategories) {
            if(category.getId() == id) return category;
        }

        return null;
    }

    public void removeAllCategories(){
        allCategories.clear();
    }

    public void changeCategory(int targetCategoryId, String categoryNewName, int categoryNewColor) {
        for(Category category : allCategories) {
            if(category.getId() == targetCategoryId) {
                category.setName(categoryNewName);
                category.setColor(categoryNewColor);
                FileWorker.getInstance(context).rewriteCategoriesList();
                return;
            }
        }
    }
}
