package com.gorih.familycoffers.model;

import android.util.Log;

import com.gorih.familycoffers.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Categories {
    private HashMap<String, Category> allCategories = new HashMap<>();
    private static Categories categoriesInstance = null;

    public static Categories getInstance() {
        if (categoriesInstance == null) {
            categoriesInstance = new Categories();
        }
        return categoriesInstance;
    }

    private Categories() {
        init();
    }

    private void init() {
        allCategories.put("Food", new Category("Food", R.color.colorFoodCategory, R.mipmap.ic_food_variant));
        allCategories.put("Car", new Category("Car", R.color.colorCarCategory, R.mipmap.ic_car));
        allCategories.put("Sports", new Category("Sports", R.color.colorSportsCategory, R.mipmap.ic_bike));
        allCategories.put("Health", new Category("Health", R.color.colorHealthCategory, R.mipmap.ic_heart_pulse));
        allCategories.put("Luxury", new Category("Luxury", R.color.colorLuxuryCategory, R.mipmap.ic_diamond));
        allCategories.put("Home", new Category("Home", R.color.colorHomeCategory, R.mipmap.ic_home_variant));
        allCategories.put("Shit", new Category("Shit", R.color.colorShitCategory, R.mipmap.ic_emoticon_poop));
    }

    public HashMap<String, Category> getAllCategoriesMap() { return allCategories; }

    public ArrayList<Category> getAllCategoriesList() {
        Collection<Category> collection = allCategories.values();
        return new ArrayList<>(collection);
    }

    public void addValueToCategory(String categoryName, float valueToAdd) {
        Category category = allCategories.get(categoryName);

        if (category != null){
            category.addValueToSum(valueToAdd);
        } else {
            Log.d("Categories", "Wrong category name");
        }
    }

    public void removeAllTotalValues() {
        for (Category cat : allCategories.values()) {
            cat.eraseSum();
        }
    }
}
