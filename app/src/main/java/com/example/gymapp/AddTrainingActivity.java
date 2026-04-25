package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddTrainingActivity extends AppCompatActivity {

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

            String content;
            String item = getSelectedItemFromSpinner(R.id.list).toString();

            if (FileHelper.hasSaveFileBeenCreated(this)) {
                content = FileHelper.readFromSaveFile(this) + "\n" + item;
            }
            else {
                content = item;
            }

            FileHelper.writeToSaveFile(this, content);

            Intent intent = new Intent(AddTrainingActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}