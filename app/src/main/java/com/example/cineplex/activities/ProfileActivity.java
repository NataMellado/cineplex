package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineplex.R;
import com.example.cineplex.models.User;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_profile);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener referencia al username y rut
        TextView username = findViewById(R.id.username);
        TextView rut = findViewById(R.id.rut);

        // Establecer el texto del username y rut
        username.setText("Usuario: " + User.getCurrentUser().getUsername());
        rut.setText("Rut: " + User.getCurrentUser().getRut());

        // Configuración del botón logout
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
            User.setCurrentUser(null);
            startActivity(i);
        });


        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this, CarteleraActivity.class);
            startActivity(i);
        });



        // Configuración del botón Profile
        Button profileButton = findViewById(R.id.button_profile);
        profileButton.setOnClickListener(view -> {
            Intent i = new Intent(ProfileActivity.this, ProfileActivity.class);
            startActivity(i);
        });


    }




}
