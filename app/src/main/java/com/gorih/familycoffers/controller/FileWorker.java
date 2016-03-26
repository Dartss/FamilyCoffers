package com.gorih.familycoffers.controller;


import android.content.Context;

import com.google.gson.Gson;
import com.gorih.familycoffers.model.Category;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileWorker {
    private static FileWorker fileWorker;
    private final String filename = "savedCategories.json";
    private Context context;

    public static void init(Context context) {
        if (fileWorker != null) {
            return;
        }
        fileWorker = new FileWorker(context);
    }

    public static FileWorker getInstance() {
        return fileWorker;
    }

    private FileWorker(Context context) {

        this.context = context;
    }

    protected void writeCategoriesList (List<Category> categories) {
        Gson gson = new Gson();

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(filename, Context.MODE_PRIVATE)));
            for (Category category : categories) {
                String jsonRepresentation = gson.toJson(category);
                bw.write(jsonRepresentation);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeCategory(Category category) {
        Gson gson = new Gson();

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    context.openFileOutput(filename, Context.MODE_APPEND)));
            String jsonRepresentation = gson.toJson(category);
            bw.write(jsonRepresentation);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<Category> readCategories() throws IOException {
        ArrayList<Category> categories = new ArrayList<>();
        Gson gson = new Gson();

        BufferedReader buffered = new BufferedReader(new InputStreamReader(
                context.openFileInput(filename)));
        String readString;
        while ((readString = buffered.readLine()) != null) {
            Category spent = gson.fromJson(readString, Category.class);
            categories.add(spent);
        }
        return categories;
    }

    public void removeFile() {
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }
}
