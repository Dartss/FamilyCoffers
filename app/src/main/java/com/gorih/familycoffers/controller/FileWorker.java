//package com.gorih.familycoffers.controller;
//
//
//import android.content.Context;
//
//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//
//public class FileWorker {
//    private static FileWorker fileWorker;
//    private final String filename = "familyMemberInfo.json";
//    private Context context;
//
//    public static FileWorker getInstance(Context context) {
//        if (fileWorker == null) {
//            fileWorker = new FileWorker(context);
//        }
//
//        return fileWorker;
//    }
//
//    private FileWorker(Context context) {
//
//        this.context = context;
//    }
//
//    public void writeFamilyMemberInfo (FamilyMember member) {
//        Gson gson = new Gson();
//
//        try {
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
//                    context.openFileOutput(filename, Context.MODE_PRIVATE)));
//            String jsonRepresentation = gson.toJson(member);
//            bw.write(jsonRepresentation);
//            bw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void removeFile() {
//        File file = new File(context.getFilesDir(), filename);
//        file.delete();
//    }
//
//    public FamilyMember readFamilyMemberInfo() throws IOException {
//        FamilyMember familyMember;
//        Gson gson = new Gson();
//
//        BufferedReader buffered = new BufferedReader(new InputStreamReader(
//                context.openFileInput(filename)));
//        String readString;
//        readString = buffered.readLine();
//        familyMember = gson.fromJson(readString, FamilyMember.class);
//
//        return familyMember;
//    }
//
//    //
////    public void writeCategory(Category category) {
////        Gson gson = new Gson();
////
////        try {
////            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
////                    context.openFileOutput(filename, Context.MODE_APPEND)));
////            String jsonRepresentation = gson.toJson(category);
////            bw.write(jsonRepresentation);
////            bw.newLine();
////            bw.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }
//}
