package com.example.gymapp;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView mainMenu;
    ImageButton mainMenuButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            populateMainMenu();
            mainMenuSetClickListener();
            setMainMenuButtonOnClickListener();
            view();
        }

    @Override
    protected void onResume() {
        super.onResume();
        view();
    }
    private void populateMainMenu() {
        String[] items = getResources().getStringArray(R.array.main_menu_options);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );

        mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setAdapter(adapter);

        mainMenu.setVisibility(INVISIBLE);
    }
    private void setMainMenuButtonOnClickListener() {
        mainMenuButton = findViewById(R.id.mainMenuButton);

        mainMenuButton.setOnClickListener(b -> {
            mainMenu.showDropDown();
        });
    }
    private void mainMenuSetClickListener() {
        mainMenu.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            changePage(selected);
        });
    }
    private void changePage(String destinationPageName) {

        Class<?> destClass;
        switch(destinationPageName.toLowerCase()) {
            case "aggiungi allenamento":
                destClass = AddTrainingActivity.class;
                break;

            case "visualizza scheda":
                destClass = Schedule.class;
                break;

            case "note":
                destClass = Notes.class;
                break;

            default:
                throw new IllegalArgumentException(destinationPageName + " does not exist.");
        }

        Intent dest = new Intent(MainActivity.this, destClass);

        startActivity(dest);
    }
    private void view()
    {
        if (FileHelper.hasSaveFileBeenCreated(this, SaveFile.TRAIN_DAYS))
        {
            String content = FileHelper.readFromSaveFile(this, SaveFile.TRAIN_DAYS).toLowerCase();
            String[] lines = content.split("\n");
            for (String day : lines)
            {
                day = day.trim();
                switch (day)
                {
                    case "lunedì":
                    {
                        findViewById(R.id.vLunedi).setVisibility(VISIBLE);
                        break;
                    }
                    case "martedì":
                    {
                        findViewById(R.id.vMartedi).setVisibility(VISIBLE);
                        break;
                    }
                    case "mercoledì":
                    {
                        findViewById(R.id.vMercoledi).setVisibility(VISIBLE);
                        break;
                    }
                    case "giovedì":
                    {
                        findViewById(R.id.vGiovedi).setVisibility(VISIBLE);
                        break;
                    }
                    case "venerdì":
                    {
                        findViewById(R.id.vVenerdi).setVisibility(VISIBLE);
                        break;
                    }
                    case "sabato":
                    {
                        findViewById(R.id.vSabato).setVisibility(VISIBLE);
                        break;
                    }
                    case "domenica":
                    {
                        findViewById(R.id.vDomenica).setVisibility(VISIBLE);
                        break;
                    }
                }
            }
        }
    }
}
