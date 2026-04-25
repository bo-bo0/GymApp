package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AddTrainingActivity extends AppCompatActivity {

    // c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_training);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setButtonListener();
    }

    private Object getSelectedItemFromSpinner(int id) {
        Spinner spinner = findViewById(id);
        return spinner.getSelectedItem();
    }

    private void setButtonListener() {
        Button btnSave = findViewById(R.id.button2);
        btnSave.setOnClickListener(v -> {
            Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
            startActivity(intent);
            try {
                var fw = openFileOutput("save.txt", MODE_PRIVATE);
                fw.write(getSelectedItemFromSpinner(R.id.list).toString().getBytes());
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}