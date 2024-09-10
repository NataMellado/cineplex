package com.example.cineplex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Obtener el Id de la pelicula
        Intent intent = getIntent();
        String movieId = intent.getStringExtra("MOVIE_ID");

        // Obtener referencia a ImageView y TextView
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitleView = findViewById(R.id.movie_title);

        // Verificar el valor de movieId y ajustr el ImageView y TextView según corresponda
        if ("Movie1".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image1);
            movieTitleView.setText("Interestelar");
        } else if ("Movie2".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image3);
            movieTitleView.setText("Alien: Resurrection");
        } else if ("Movie3".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image4);
            movieTitleView.setText("Inception");
        } else if ("Movie4".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image5);
            movieTitleView.setText("Lord Of The Rings");
        } else if ("Movie5".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image2);
            movieTitleView.setText("It end with us");
        } else if ("Movie6".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image6);
            movieTitleView.setText("Dune 2");
        }



        // Declara variables
        Spinner spinner;
        RadioGroup radioGroupDate;
        RadioGroup radioGroupTime;

        // Inicializa las variables
        radioGroupDate = findViewById(R.id.radioGroupDate);
        radioGroupTime = findViewById(R.id.radioGroupTime);
        spinner = findViewById(R.id.header_spinner);

        // Configuración del spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cinema_spinner_data, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Configuración del botón Continuar
        Button continueButton;
        continueButton = findViewById(R.id.button_continuar);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el valor seleccionado en el Spinner
                String selectedCine = spinner.getSelectedItem().toString();

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
                    // Crear un Intent para la nueva Activity
                    Intent intent = new Intent(MovieActivity.this, SeatActivity.class);
                    intent.putExtra("CINE", selectedCine);
                    intent.putExtra("DATE", selectedDate);
                    intent.putExtra("SCHEDULE", selectedSchedule);
                    intent.putExtra("MOVIE_ID", movieId);

                    // Iniciar la nueva Activity
                    startActivity(intent);
                }
            }
        });

        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MovieActivity.this, CarteleraActivity.class);
                startActivity(i);
            }
        });


    }
}
