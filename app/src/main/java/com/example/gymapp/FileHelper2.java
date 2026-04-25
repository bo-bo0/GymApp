package com.example.gymapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public final class FileHelper2{
    private FileHelper2() {}
    public static String readFromSaveFile(Context context) {
        try {
            var fis = context.openFileInput("save.kgt");
            var isr = new InputStreamReader(fis);
            var reader = new BufferedReader(isr);
            var sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank())
                { sb.append("\n").append(line); }
            }

            String savedText = sb.toString();
            reader.close();

            return savedText;
        }
        catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    public static void writeToSaveFile(Context context, String content) {
        try {
            var fw = context.openFileOutput("save.kgt", MODE_PRIVATE);
            fw.write(content.getBytes());
            fw.close();
        }
        catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    public static boolean hasSaveFileBeenCreated(Context context) {
        try {
            var fis = context.openFileInput("save.kgt");
            fis.close();
        }
        catch (IOException ex) {
            return false;
        }
        return true;
    }
}