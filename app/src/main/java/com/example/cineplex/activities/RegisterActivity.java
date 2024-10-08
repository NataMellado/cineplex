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

public class RegisterActivity extends AppCompatActivity {

    EditText txtUsername, txtRut, txtPassword, txtConfirmPassword;
    Button btnRegister;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_register);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a los elementos
        txtUsername = findViewById(R.id.username);
        txtRut = findViewById(R.id.rut);
        txtPassword = findViewById(R.id.password);
        txtConfirmPassword = findViewById(R.id.confirmPassword);
        btnRegister = findViewById(R.id.register);

        // Configuración del botón de registro
        btnRegister.setOnClickListener(view -> {
            String username = txtUsername.getText().toString();
            String rut = txtRut.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPassword = txtConfirmPassword.getText().toString();

            // Validaciones
            if (username.isEmpty() || rut.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();
            } else {
                // Obtener la instancia de DatabaseHelper
                DatabaseHelper dbHelper = DatabaseHelper.getInstance(getApplicationContext());

                // Verificar si el rut o el nombre de usuario ya existen
                if (dbHelper.usernameExists(username)) {
                    Toast.makeText(getApplicationContext(), "El nombre de usuario ya está registrado", Toast.LENGTH_SHORT).show();
                } else if (dbHelper.rutExists(rut)) {
                    Toast.makeText(getApplicationContext(), "El RUT ya está registrado", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else {

                    // Insertar el nuevo usuario en la base de dato
                    long userId = dbHelper.addUser(username, rut, password);
                    if (userId != -1) { // Si la inserción fue exitosa
                        user = new User(userId, username, rut);
                        Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                    }

                    // Pasar el usuario a la actividad de inicio de sesión
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.putExtra("USER", user);
                    startActivity(intent);
                }
            }
        });

        // Configuración del enlace de login
        TextView registerPrompt = findViewById(R.id.loginPrompt);
        registerPrompt.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

}
