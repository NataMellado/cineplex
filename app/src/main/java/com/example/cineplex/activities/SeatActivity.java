package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineplex.R;
import com.example.cineplex.helpers.FirebaseDataHelper;
import com.example.cineplex.helpers.MovieDisplayHelper;
import com.example.cineplex.helpers.NavigationHelper;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.Seat;
import com.example.cineplex.models.Ticket;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeatActivity extends AppCompatActivity {

    private GridLayout seatContainer;
    private FirebaseDataHelper firebaseDataHelper;
    private List<CheckBox> seatCheckBoxes = new ArrayList<>(); // Lista para almacenar los CheckBoxes dinámicos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        NavigationHelper.setupBottomNavigation(this);

        // Obtener el Intent y extraer los datos de la película
        Intent intent = getIntent();
        String theaterKey = intent.getStringExtra("theaterKey");
        Movie movie = intent.getParcelableExtra("movie");
        String selectedDate = intent.getStringExtra("selectedDate");
        String selectedSchedule = intent.getStringExtra("selectedSchedule");

        // Obtener userI del usuario logueado
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //imprimir con log el userId
        Log.d("SeatActivity", "User ID: " + userId);

        // Renderizar datos de la película en la vista
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitle = findViewById(R.id.movie_title);
        MovieDisplayHelper.setupMovieDisplay(this, movie, movieImage, movieTitle, null);

        seatContainer = findViewById(R.id.seat_container);
        firebaseDataHelper = new FirebaseDataHelper();

        // **Verificación de valores nulos antes de llamar a Firebase**
        if (theaterKey == null || movie == null || movie.getId() == null || selectedDate == null || selectedSchedule == null) {
            Toast.makeText(this, "Error al cargar la información de los asientos", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si falta algún valor importante
            return;
        }


        // Cargar los asientos dinámicamente desde Firebase
        firebaseDataHelper.loadSeatsForSchedule(theaterKey, movie.getId(), selectedDate, selectedSchedule, new FirebaseDataHelper.SeatLoadListener() {
            @Override
            public void onSeatsLoaded(Map<String, Seat> seats) {
                renderSeats(seats);
            }
        });

        // Configuración del botón continuar
        Button continueButton = findViewById(R.id.button_continuar);
        continueButton.setOnClickListener(view -> {
            List<String> selectedSeatsList = new ArrayList<>();
            int selectedSeatsCount = 0;

            // Iterar sobre la lista de CheckBoxes dinámicos
            for (CheckBox checkBox : seatCheckBoxes) {
                if (checkBox.isChecked()) {
                    selectedSeatsList.add(checkBox.getText().toString());
                    selectedSeatsCount++;
                }
            }

            if (selectedSeatsList.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes seleccionar al menos un asiento.", Toast.LENGTH_SHORT).show();
            } else {
                // Crear un objeto Ticket con los datos seleccionados
                Ticket ticket = new Ticket(
                        null,
                        movie.getId(),
                        movie.getTitle(),
                        movie.getImage_path(),
                        theaterKey,
                        "Sala 10",  // Aquí puedes ajustar el número de sala
                        selectedDate,
                        selectedSchedule,
                        selectedSeatsList,
                        selectedSeatsList.size() * 5990,
                        userId
                );

                // Guardar el ticket en Firebase
                firebaseDataHelper.createTicket(ticket);

                // Actualizar el estado de los asientos en Firebase
                firebaseDataHelper.updateSeatStatus(theaterKey, movie.getId(), selectedDate, selectedSchedule, selectedSeatsList);

                goToConfirmation(ticket, movie);
            }
        });

    }

    private void renderSeats(Map<String, Seat> seats) {
        seatContainer.removeAllViews();
        seatCheckBoxes.clear();

        for (Map.Entry<String, Seat> entry : seats.entrySet()) {
            String seatNumber = entry.getKey();
            Seat seat = entry.getValue();

            CheckBox seatCheckBox = new CheckBox(this);
            seatCheckBox.setText(seatNumber);
            seatCheckBox.setEnabled(seat.isAvailable()); // Verifica la disponibilidad con isAvailable()
            seatContainer.addView(seatCheckBox);
            seatCheckBoxes.add(seatCheckBox);
        }
    }

    private void goToConfirmation(Ticket ticket, Movie movie) {
        Intent intent = new Intent(this, ConfirmationActivity.class);
        intent.putExtra("ticket", ticket);
        intent.putExtra("movie", movie);
        Toast.makeText(getApplicationContext(), "Ticket comprado con éxito", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}
