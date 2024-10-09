package com.example.cineplex.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineplex.R;
import com.example.cineplex.models.Ticket;
import com.example.cineplex.models.User;

import java.text.NumberFormat;
import java.util.Locale;

public class ConfirmationActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_confirmation);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // imprimir en consola la id del currentUser
        System.out.println("User id desde confirmation: " + User.getCurrentUser().getUserId());

        // Obtener datos del intent
        Intent intent = getIntent();
        Ticket ticket = intent.getParcelableExtra("TICKET");
        User user = intent.getParcelableExtra("USER");

        // Obtener referencias de las vistas
        TextView textViewCinema = findViewById(R.id.cinema);
        TextView textViewDate = findViewById(R.id.date);
        TextView textViewTime = findViewById(R.id.time);
        TextView textViewSeats = findViewById(R.id.seats);
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitleView = findViewById(R.id.movie_title);
        TextView textViewPrice = findViewById(R.id.price);

        // Configurar la imagen y el título
        if (ticket != null) {
            movieImage.setImageResource(ticket.getPelicula().getImageResId());
            movieTitleView.setText(ticket.getPelicula().getTitle());
        }


        // Mostrar los datos de la película seleccionada en TextView
        textViewCinema.setText("Cine: " + ticket.getCine());
        textViewDate.setText("Fecha: " + ticket.getFecha());
        textViewTime.setText("Hora: " + ticket.getHora());
        textViewSeats.setText("Asientos: " + ticket.getAsientos());
        textViewPrice.setText("Valor: " + NumberFormat.getCurrencyInstance(new Locale("es", "CL")).format(ticket.getValor()));


        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(view -> {
            Intent i = new Intent(ConfirmationActivity.this, CarteleraActivity.class);
            // pasar el usuario a la siguiente actividad
            i.putExtra("USER", user);
            startActivity(i);
        });

    }
}