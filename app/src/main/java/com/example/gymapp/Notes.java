package com.example.gymapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Notes extends AppCompatActivity {

    private EditText editTitle;
    private EditText editContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ImageButton btnDelete = findViewById(R.id.btn_delete);
        editTitle = findViewById(R.id.edit_note_title);
        editContent = findViewById(R.id.edit_note_content);


        btnDelete.setOnClickListener(v -> {
            editTitle.setText("");
            editContent.setText("");
            saveNote();
        });
    }
    private void saveNote()
    {
            FloatingActionButton fabSave = findViewById(R.id.fab_save);
            fabSave.setOnClickListener(v -> {
                String title = editTitle.getText().toString();
                String content = editContent.getText().toString();
                if (!title.isEmpty() && !content.isEmpty()) {
                    // saveNoteToDatabase(title, content);
                    Toast.makeText(Notes.this, "Nota salvata", Toast.LENGTH_SHORT).show();
                }
            });
    }
}

