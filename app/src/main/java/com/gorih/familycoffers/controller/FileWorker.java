package com.gorih.familycoffers.controller;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Category;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileWorker {
    private static FileWorker fileWorker;
    private final String filename = "familyMemberInfo.json";
    private Context context;

    public static FileWorker getInstance(Context context) {
        if (fileWorker == null) {
            fileWorker = new FileWorker(context);
        }

        return fileWorker;
    }

    private FileWorker(Context context) {

        this.context = context;
    }

    public void removeFile() {
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }

    public ArrayList<Category> readCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        Gson gson = new Gson();
        try {
            BufferedReader buffered = new BufferedReader(new InputStreamReader(
                    context.openFileInput(filename)));
            String reader;
            while ( (reader = buffered.readLine()) != null) {
                Category newCat = gson.fromJson(reader, Category.class);
                categories.add(newCat);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public void rewriteCategoriesList() {
        Gson gson = new Gson();
        removeFile();
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(filename, Context.MODE_APPEND)));
            for (Category category : Categories.instance.getAllCategoriesList()) {
                String jsonRepresentation = gson.toJson(category);
                bw.write(jsonRepresentation);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
