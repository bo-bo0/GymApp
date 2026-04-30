package com.example.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private AutoCompleteTextView mainMenu;
    private ImageButton mainMenuButton;

    private final int[] checkIds = {
            R.id.vLunedi, R.id.vMartedi, R.id.vMercoledi,
            R.id.vGiovedi, R.id.vVenerdi, R.id.vSabato, R.id.vDomenica
    };

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
        runWeeklyFlow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        runWeeklyFlow();
    }

    private void runWeeklyFlow() {
        checkAndResetWeeklyProgress();
        refreshCheckmarks();
    }

    private void populateMainMenu() {
        String[] items = getResources().getStringArray(R.array.main_menu_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        mainMenu = findViewById(R.id.mainMenu);
        mainMenu.setAdapter(adapter);
        mainMenu.setVisibility(View.INVISIBLE);
    }

    private void setMainMenuButtonOnClickListener() {
        mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(b -> mainMenu.showDropDown());
    }

    private void mainMenuSetClickListener() {
        mainMenu.setOnItemClickListener((parent, view, position, id) -> {
            String selected = (String) parent.getItemAtPosition(position);
            changePage(selected);
        });
    }

    private void changePage(String destinationPageName) {
        Class<?> destClass;
        switch (destinationPageName.toLowerCase()) {
            case "aggiungi allenamento": destClass = AddTrainingActivity.class; break;
            case "visualizza scheda":     destClass = Schedule.class; break;
            case "note":                 destClass = Notes.class; break;
            default:
                Toast.makeText(this, "Pagina non trovata", Toast.LENGTH_SHORT).show();
                return;
        }
        startActivity(new Intent(MainActivity.this, destClass));
    }

    private void checkAndResetWeeklyProgress() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY); // Forza lunedì come inizio settimana

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
        int currentYear = calendar.get(Calendar.YEAR);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        int savedWeek = prefs.getInt("last_saved_week", -1);
        int savedYear = prefs.getInt("last_saved_year", -1);

        if (currentWeek != savedWeek || currentYear != savedYear) {

            FileHelper.writeToSaveFile(this, "", SaveFile.TRAIN_DAYS);

            prefs.edit()
                    .putInt("last_saved_week", currentWeek)
                    .putInt("last_saved_year", currentYear)
                    .apply();

            Toast.makeText(this, "Nuova settimana: progressi resettati", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshCheckmarks() {

        for (int id : checkIds) {
            View v = findViewById(id);
            if (v != null) v.setVisibility(View.INVISIBLE);
        }

        if (FileHelper.hasSaveFileBeenCreated(this, SaveFile.TRAIN_DAYS)) {
            String content = FileHelper.readFromSaveFile(this, SaveFile.TRAIN_DAYS).toLowerCase();
            String[] lines = content.split("\n");

            for (String day : lines) {
                day = day.trim();
                int viewId = 0;
                switch (day) {
                    case "lunedì":    viewId = R.id.vLunedi; break;
                    case "martedì":   viewId = R.id.vMartedi; break;
                    case "mercoledì": viewId = R.id.vMercoledi; break;
                    case "giovedì":   viewId = R.id.vGiovedi; break;
                    case "venerdì":   viewId = R.id.vVenerdi; break;
                    case "sabato":    viewId = R.id.vSabato; break;
                    case "domenica":  viewId = R.id.vDomenica; break;
                }
                if (viewId != 0) {
                    findViewById(viewId).setVisibility(View.VISIBLE);
                }
            }
        }
    }
}