package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.cineplex.R;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.Ticket;
import com.example.cineplex.models.User;

public class MovieActivity extends AppCompatActivity {

    Spinner spinner;
    RadioGroup radioGroupDate;
    RadioGroup radioGroupTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_movie);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el usuario de la actividad anterior
        Intent intent2 = getIntent();
        User user = intent2.getParcelableExtra("USER");

        //Obtener el objeto Movie desde el Intent
        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("MOVIE");

        // Obtener referencia a imagen, título y descripción
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitle = findViewById(R.id.movie_title);
        TextView movieDescription = findViewById(R.id.movie_description);


        // Establecer los valores
        if(movie != null) {
            movieImage.setImageResource(movie.getImageResId());
            movieTitle.setText(movie.getTitle());
            movieDescription.setText(movie.getDescription());
        }


        // Almacena en las variables su referencia por ID
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
        continueButton.setOnClickListener(view -> {
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
                // Crear el objeto Ticket
                Ticket ticket = new Ticket(movie, selectedCine, "10", selectedDate, selectedSchedule, "", 0, user.getUserId());

                // Pasar el objeto Ticket a la nueva Activity de asientos
                Intent intent1 = new Intent(MovieActivity.this, SeatActivity.class);
                intent1.putExtra("TICKET", ticket);
                intent1.putExtra("USER", user);

                // Iniciar la nueva Activity
                startActivity(intent1);
            }
        });

        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(view -> {
            Intent i = new Intent(MovieActivity.this, CarteleraActivity.class);
            startActivity(i);
        });


    }
}
