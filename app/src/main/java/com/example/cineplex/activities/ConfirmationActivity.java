package com.example.cineplex.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cineplex.R;
import com.example.cineplex.helpers.MovieDisplayHelper;
import com.example.cineplex.helpers.NavigationHelper;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.Ticket;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_confirmation);
        NavigationHelper.setupBottomNavigation(this);

        // Obtener Ticket de Intent
        Ticket ticket = getIntent().getParcelableExtra("ticket");
        Movie movie = getIntent().getParcelableExtra("movie");


        // Obtener referencias de las vistas
        TextView textViewCinema = findViewById(R.id.cinema);
        TextView textViewDate = findViewById(R.id.date);
        TextView textViewTime = findViewById(R.id.time);
        TextView textViewSeats = findViewById(R.id.seats);
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitleView = findViewById(R.id.movie_title);
        TextView textViewPrice = findViewById(R.id.price);

        // Verificar que el ticket no sea nulo antes de acceder a sus datos
        if (ticket != null) {
            // Usar MovieDisplayHelper para configurar la imagen y título de la película
            MovieDisplayHelper.setupMovieDisplay(this, movie, movieImage, movieTitleView, null);

            // Mostrar los datos del ticket en los TextViews
            textViewCinema.setText("Cine: " + ticket.getCine());
            textViewDate.setText("Fecha: " + ticket.getFecha());
            textViewTime.setText("Hora: " + ticket.getHora());
            textViewSeats.setText("Asientos: " + ticket.getAsientos());
            textViewPrice.setText("Precio: " + NumberFormat.getCurrencyInstance(new Locale("es", "CL")).format(ticket.getValor()));

        }




    }
}