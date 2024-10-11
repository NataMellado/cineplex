package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineplex.R;
import com.example.cineplex.database.DatabaseHelper;
import com.example.cineplex.models.Movie;


public class CarteleraActivity extends AppCompatActivity {

    // Declarar una variable DatabaseHelper
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_cartelera);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener una instancia de DatabaseHelper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Crear un arreglo de IDs de CardView
        int[] cardViewIds = {
                R.id.cardViewMovie1,
                R.id.cardViewMovie2,
                R.id.cardViewMovie3,
                R.id.cardViewMovie4,
                R.id.cardViewMovie5,
                R.id.cardViewMovie6
        };


        // Configurar los listeners para cada CardView
        for (int i = 0; i < cardViewIds.length; i++) {
            CardView cardView = findViewById(cardViewIds[i]);
            int movieId = i + 1;

            cardView.setOnClickListener(view -> {
                Movie selectedMovie = databaseHelper.getMovieById(movieId);

                Intent intent = new Intent(CarteleraActivity.this, MovieActivity.class);
                intent.putExtra("movie", selectedMovie);
                startActivity(intent);
            });

        }

        // Configuración del botón Profile
        Button profileButton = findViewById(R.id.button_profile);
        profileButton.setOnClickListener(view -> {
            Intent i = new Intent(CarteleraActivity.this, ProfileActivity.class);
            startActivity(i);
        });

        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(view -> {
            Intent i = new Intent(CarteleraActivity.this, CarteleraActivity.class);
            startActivity(i);
        });

    }

}