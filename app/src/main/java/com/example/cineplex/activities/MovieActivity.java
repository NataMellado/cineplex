package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineplex.R;
import com.example.cineplex.helpers.MovieDisplayHelper;
import com.example.cineplex.helpers.NavigationHelper;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.Schedule;

import java.util.Map;

public class MovieActivity extends AppCompatActivity {

    RadioGroup radioGroupDate;
    RadioGroup radioGroupTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        NavigationHelper.setupBottomNavigation(this);

        //Obtener el Intent y extraer el cine y la película
        Intent intent = getIntent();
        String theaterKey = intent.getStringExtra("theaterKey");
        Movie movie = intent.getParcelableExtra("movie");

        // Configuración de la interfaz con los datos de la película
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieDescription = findViewById(R.id.movie_description);

        // Usar MovieDisplayHelper para configurar la imagen, título y descripción
        MovieDisplayHelper.setupMovieDisplay(this, movie, movieImage, movieTitle, movieDescription);

        if (movie != null) {
            showDatesAndTimes(movie.getDates());
        }

        // Obtener referencias a los RadioGroups y el Spinner
        radioGroupDate = findViewById(R.id.radioGroupDate);
        radioGroupTime = findViewById(R.id.radioGroupTime);

        // Configuración del botón Continuar
        Button continueButton = findViewById(R.id.button_continuar);
        continueButton.setOnClickListener(view -> {

            // Obtener el ID del RadioButton seleccionado en RadioGroupDate
            int selectedDateId = radioGroupDate.getCheckedRadioButtonId();
            RadioButton selectedDateButton = findViewById(selectedDateId);
            String selectedDate = selectedDateButton != null ? selectedDateButton.getText().toString() : "";

            // Obtener el ID del RadioButton seleccionado en RadioGroupTime
            int selectedScheduleId = radioGroupTime.getCheckedRadioButtonId();
            RadioButton selectedScheduleButton = findViewById(selectedScheduleId);
            String selectedSchedule = selectedScheduleButton != null ? selectedScheduleButton.getText().toString() : "";

            // Verificar que la fecha y hora no estén vacías
            if (selectedDate.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes eleccionar una fecha", Toast.LENGTH_SHORT).show();
            } else if (selectedSchedule.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes seleccionar una hora", Toast.LENGTH_SHORT).show();
            } else {
                goToSeatSelection(theaterKey, movie, selectedDate, selectedSchedule);

            }
        });
    }

    // Método para mostrar fechas y horarios en RadioGroups
    private void showDatesAndTimes(Map<String, Schedule> dates) {
        // Obtener referencias a los RadioButton de las fechas
        RadioButton[] dateButtons = {
                findViewById(R.id.radioButtonDate1),
                findViewById(R.id.radioButtonDate2),
                findViewById(R.id.radioButtonDate3),
                findViewById(R.id.radioButtonDate4)
        };

        // Configurar los RadioButton para las fechas
        int index = 0;
        String firstDate = null;
        for (String date : dates.keySet()) {
            if (index < dateButtons.length) {
                dateButtons[index].setText(date);
                dateButtons[index].setVisibility(View.VISIBLE);
                dateButtons[index].setOnClickListener(v -> showTimes(dates.get(date)));
                if (firstDate == null) {
                    firstDate = date;
                }
                index++;
            }
        }

        // Mostrar los horarios para la primera fecha
        if (firstDate != null) {
            showTimes(dates.get(firstDate));
        }

    }

    private void showTimes(Schedule schedule) {
        // Obtener referencias a los RadioButton de los horarios
        RadioButton[] timeButtons = {
                findViewById(R.id.radioButtonTime1),
                findViewById(R.id.radioButtonTime2),
                findViewById(R.id.radioButtonTime3),
        };

        // Configurar los RadioButton para los horarios
        int index = 0;
        if (schedule != null && schedule.getTimes() != null) {
            for (String time : schedule.getTimes().keySet()) {
                if (index < timeButtons.length) {
                    timeButtons[index].setText(time);
                    timeButtons[index].setVisibility(View.VISIBLE);
                    index++;
                }
            }
        }
    }

    private void goToSeatSelection(String theaterKey, Movie movie, String selectedDate, String selectedSchedule) {
        Intent intent = new Intent(this, SeatActivity.class);
        intent.putExtra("theaterKey", theaterKey);
        intent.putExtra("movie", movie);
        intent.putExtra("selectedDate", selectedDate);
        intent.putExtra("selectedSchedule", selectedSchedule);
        startActivity(intent);
    }



}
