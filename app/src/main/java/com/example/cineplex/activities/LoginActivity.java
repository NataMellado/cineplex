package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineplex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtPassword;
    Button btnLogin;
    FirebaseAuth firebaseAuth;

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

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Referencias a los elementos
        txtUser = findViewById(R.id.user);
        txtPassword =  findViewById(R.id.password);
        btnLogin = findViewById(R.id.login);

        // Configuración del botón de inicio de sesión
        btnLogin.setOnClickListener(view -> {
            String email = txtUser.getText().toString();
            String password = txtPassword.getText().toString();

            // Validaciones
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes ingresar todos los datos", Toast.LENGTH_SHORT).show();
            } else {
                // Autenticar al usuario con Firebase
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(LoginActivity.this, CarteleraActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Las credenciales son incorrectas", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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