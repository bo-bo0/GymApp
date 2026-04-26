package com.example.gymapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public final class FileHelper{
    private FileHelper() {}
    public static String readFromSaveFile(Context context, SaveFile file) {
        try {
            var fis = context.openFileInput(getSaveFileName(file));
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

    public static void writeToSaveFile(Context context, String content, SaveFile file) {
        try {
            var fw = context.openFileOutput(getSaveFileName(file), MODE_PRIVATE);
            fw.write(content.getBytes());
            fw.close();
        }
        catch (IOException ex) {
            throw new RuntimeException();
        }
    }

    public static boolean hasSaveFileBeenCreated(Context context, SaveFile file) {
        try {
            var fis = context.openFileInput(getSaveFileName(file));
            fis.close();
        }
        catch (IOException ex) {
            return false;
        }
        return true;
    }

    private static String getSaveFileName(SaveFile file) {
        switch (file) {
            case TRAIN_DAYS:
                return "save.txt";

            case SCHEDULE_KG:
                return "kg.txt";

            default:
                throw new IllegalArgumentException(file + " is an invalid SaveFile type");
        }
    }
}
