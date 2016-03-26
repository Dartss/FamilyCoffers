package com.gorih.familycoffers.model;

import com.gorih.familycoffers.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Categories {
    private List<Category> allCategories = new ArrayList<>();
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
        allCategories.add(new Category("Food", R.color.colorFoodCategory, R.mipmap.ic_food_variant));
        allCategories.add(new Category("Car", R.color.colorCarCategory, R.mipmap.ic_car));
        allCategories.add(new Category("Sports", R.color.colorSportsCategory, R.mipmap.ic_bike));
        allCategories.add(new Category("Health", R.color.colorHealthCategory, R.mipmap.ic_heart_pulse));
        allCategories.add(new Category("Luxury", R.color.colorLuxuryCategory, R.mipmap.ic_diamond));
        allCategories.add(new Category("Home", R.color.colorHomeCategory, R.mipmap.ic_home_variant));
        allCategories.add(new Category("Shit", R.color.colorShitCategory, R.mipmap.ic_emoticon_poop));
    }

    public HashMap<String, Category> getAllCategoriesMap() {
        HashMap<String, Category> categoriesMap = new HashMap<>();

        for(Category category : allCategories) {
            categoriesMap.put(category.getName(), category);
        }

        return categoriesMap;
    }

    public List<Category> getAllCategoriesList() {
        return allCategories;
    }
}
