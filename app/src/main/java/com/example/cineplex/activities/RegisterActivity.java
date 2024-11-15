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

import com.example.cineplex.R;
import com.example.cineplex.helpers.FirebaseDataHelper;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    EditText txtUsername, txtRut, txtPassword, txtConfirmPassword;
    Button btnRegister;
    FirebaseAuth firebaseAuth;

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

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Referencias a los elementos
        txtUsername = findViewById(R.id.username);
        txtRut = findViewById(R.id.rut);
        txtPassword = findViewById(R.id.password);
        txtConfirmPassword = findViewById(R.id.confirmPassword);
        btnRegister = findViewById(R.id.register);


        // Configuración del botón de registro
        btnRegister.setOnClickListener(view -> {
            String email = txtUsername.getText().toString();
            String rut = txtRut.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPassword = txtConfirmPassword.getText().toString();

            // Validaciones
            if (email.isEmpty() || rut.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(getApplicationContext(), "El email no es válido", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                // Registro en Firebase
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                String userId = firebaseAuth.getCurrentUser().getUid();

                                // Crear instancia de FirebaseDataHelper y llamar a createUser
                                FirebaseDataHelper firebaseDataHelper = new FirebaseDataHelper();
                                firebaseDataHelper.createUser(userId, email, rut, new FirebaseDataHelper.UserCreationListener() {
                                    @Override
                                    public void onUserCreated() {
                                        Toast.makeText(getApplicationContext(), "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    }

                                    @Override
                                    public void onError(String error) {
                                        Toast.makeText(getApplicationContext(), "Error al crear el usuario: " + error, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String errorMessage = task.getException().getMessage();
                                System.out.println("Error en el registro: " + errorMessage);
                                Toast.makeText(getApplicationContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
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


