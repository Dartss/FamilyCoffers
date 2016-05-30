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
//        allCategories.add(new Category("Food", Color.parseColor("#00e676"), R.mipmap.ic_food_variant));
//        allCategories.add(new Category("Car", Color.parseColor("#a1887f"), R.mipmap.ic_car));
//        allCategories.add(new Category("Sports", Color.parseColor("#1de9b6"), R.mipmap.ic_bike));
//        allCategories.add(new Category("Health", Color.parseColor("#e91e63"), R.mipmap.ic_heart_pulse));
//        allCategories.add(new Category("Luxury", Color.parseColor("#304ffe"), R.mipmap.ic_diamond));
//        allCategories.add(new Category("Home", Color.parseColor("#ffb74d"), R.mipmap.ic_home_variant));
//        allCategories.add(new Category("Shit", Color.parseColor("#827717"), R.mipmap.ic_emoticon_poop));
    }

    public ArrayList<Category> getAllCategoriesList() {
        return allCategories;
    }

    public void addNewCategory(String newCategoryName, int newCategoryColor) {
        Category newCategory = new Category(newCategoryName, newCategoryColor, Constants.DEFAULT_CATEGORY_ICON);
        newCategory.setId(allCategories.size());
        allCategories.add(newCategory);
        Log.d(TAG, "New category added:"+newCategoryName+" id= "+newCategory.getId());
    }

    public Category findCategoryById(int id) {
        for(Category category : allCategories) {
            if(category.getId() == id) return category;
        }

        return null;
    }
}
