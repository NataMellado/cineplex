package com.example.cineplex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirmation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener datos del intent
        Intent intent = getIntent();
        String cine = intent.getStringExtra("CINE");
        String date = intent.getStringExtra("DATE");
        String time = intent.getStringExtra("SCHEDULE");
        String selectedOptions = intent.getStringExtra("SEATS");
        String movieId = intent.getStringExtra("MOVIE_ID");

        // Obtener reverencias de las vistas
        TextView textViewCinema = findViewById(R.id.cinema);
        TextView textViewDate = findViewById(R.id.date);
        TextView textViewTime = findViewById(R.id.time);
        TextView textViewSeats = findViewById(R.id.seats);
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitleView = findViewById(R.id.movie_title);

        // Configurar la imagen y el título basado en la ID de la película
        if ("Movie1".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image1);
            movieTitleView.setText("Película: Interestelar");
        } else if ("Movie2".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image3);
            movieTitleView.setText("Película: Alien Resurrection");
        } else if ("Movie3".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image4);
            movieTitleView.setText("Película: Inception");
        } else if ("Movie4".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image5);
            movieTitleView.setText("Película: Lord Of The Rings");
        } else if ("Movie5".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image2);
            movieTitleView.setText("Película: It end with us");
        } else if ("Movie6".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image6);
            movieTitleView.setText("Película: Dune 2");
        }

        // Mostrar los datos de la película seleccionada en TextView
        textViewCinema.setText("Cine: " + cine);
        textViewDate.setText("Fecha: " + date);
        textViewTime.setText("Hora: " + time);
        textViewSeats.setText("Asientos: " + selectedOptions);

        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmationActivity.this, CarteleraActivity.class);
                startActivity(i);
            }
        });


    }
}