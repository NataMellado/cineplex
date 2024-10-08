package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineplex.database.DatabaseHelper;
import com.example.cineplex.R;
import com.example.cineplex.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_login);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a los elementos
        txtUser = findViewById(R.id.user);
        txtPassword =  findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);

        // Obtener el usuario de la actividad anterior
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("USER");

        if (user != null) {
            txtUser.setText(user.getUsername());
        }

        // Configuración del botón de inicio de sesión
        btnLogin.setOnClickListener(view -> {
            String username = txtUser.getText().toString();
            String password = txtPassword.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();
            } else {
                // Obtener la instancia de DatabaseHelper
                DatabaseHelper dbHelper = DatabaseHelper.getInstance(getApplicationContext());

                // Verificar las credenciales del usuario
                if (dbHelper.verifyCredentials(username, password)) {

                    Intent I = new Intent(LoginActivity.this, CarteleraActivity.class);
                    // obtener el usuario de la base de datos y pasarlo a la siguiente actividad
                    User userLogueado = dbHelper.getUserByUsername(username);
                    I.putExtra("USER", userLogueado);
                    startActivity(I);
                    // imprimir el id del usuario logueado
                    System.out.println("ID del usuario logueado: " + userLogueado.getUserId());
                } else {
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Configuración del enlace de registro
        TextView registerPrompt = findViewById(R.id.registerPrompt);
        registerPrompt.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
    }
}