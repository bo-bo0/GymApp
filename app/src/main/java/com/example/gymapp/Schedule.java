package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class Schedule extends AppCompatActivity {

    TextInputEditText[] texts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        texts = getTextInputs();
        setSaveButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readKgDataSaveFile();
    }

    private TextInputEditText[] getTextInputs() {
        var texts = new ArrayList<TextInputEditText>();

        for (int i = 1; i <= 18; i++) {
            int id = getResources().getIdentifier("kg" + i, "id", getPackageName());
            texts.add(findViewById(id));
        }


        return texts.toArray(new TextInputEditText[]{});
    }
    private void setSaveButtonListener() {
        Button button = findViewById(R.id.Save);
        button.setOnClickListener(v -> {
            saveKgData();
            Intent intent = new Intent(Schedule.this, MainActivity.class);
            startActivity(intent);
       });
    }

    private void saveKgData() {
        var content = new StringBuilder();

        for (var t : texts) {
            content.append(t.getText()).append('\n');
        }

        FileHelper.writeToSaveFile(this, content.toString(), SaveFile.SCHEDULE_KG);
    }

    private void readKgDataSaveFile() {
        if (!FileHelper.hasSaveFileBeenCreated(this, SaveFile.SCHEDULE_KG)) {
            return;
        }

        String[] savedData =
                FileHelper.readFromSaveFile(this, SaveFile.SCHEDULE_KG).split("\n");

        savedData = Arrays.stream(savedData)
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        if (savedData.length != texts.length) {
            return;
        }

        for (int i = 0; i < savedData.length; i++) {
            savedData[i] = savedData[i].trim();
        }

        for (int i = 0; i < texts.length; i++) {
            texts[i].setText(savedData[i]);
        }
    }
}
