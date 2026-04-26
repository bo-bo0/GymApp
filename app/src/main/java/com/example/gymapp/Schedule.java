package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Schedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        saveKg();
    }
    private void saveKg() {
        Button button = findViewById(R.id.Save);
       button.setOnClickListener(v -> {
           FileHelper2.writeToSaveFile(this, "kg");
           Intent intent = new Intent(Schedule.this, MainActivity.class);
           startActivity(intent);
       });
    }
}
