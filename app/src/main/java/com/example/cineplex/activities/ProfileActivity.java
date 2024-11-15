package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cineplex.R;
import com.example.cineplex.helpers.NavigationHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_profile);

        NavigationHelper.setupBottomNavigation(this);

        //  Establecer el texto del username(email)
        TextView usernameTextView = findViewById(R.id.username);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            usernameTextView.setText(currentUser.getEmail());
        }

        // Establecer el texto del username y rut

        // Configuración del botón logout
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(view -> {
            // Cerrar sesión en Firebase
            FirebaseAuth.getInstance().signOut();
            // Volver a la pantalla de inicio de sesión
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });



    }




}
