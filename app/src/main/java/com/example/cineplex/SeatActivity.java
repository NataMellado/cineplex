package com.example.cineplex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SeatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_seat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Recibir datos del Intent
        Intent intent = getIntent();
        String cine = intent.getStringExtra("CINE");
        String date = intent.getStringExtra("DATE");
        String schedule = intent.getStringExtra("SCHEDULE");
        String movieId = intent.getStringExtra("MOVIE_ID");


        // Configurar la imagen y el título basado en la ID de la película
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitleView = findViewById(R.id.movie_title);

        if ("Movie1".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image1);
            movieTitleView.setText("Interestelar");
        } else if ("Movie2".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image3);
            movieTitleView.setText("Alien: Resurrection");
        } else if ("Movie3".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image4);
            movieTitleView.setText("Inception");
        } else if ("Movie4".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image5);
            movieTitleView.setText("Lord Of The Rings");
        } else if ("Movie5".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image2);
            movieTitleView.setText("It end with us");
        } else if ("Movie6".equals(movieId)) {
            movieImage.setImageResource(R.drawable.movie_image6);
            movieTitleView.setText("Dune 2");
        }


        // Obtener asientos
        int[] checkBoxIds = {
                R.id.checkbox1, R.id.checkbox2, R.id.checkbox3, R.id.checkbox4, R.id.checkbox5,
                R.id.checkbox6, R.id.checkbox7, R.id.checkbox8, R.id.checkbox9, R.id.checkbox10,
                R.id.checkbox11, R.id.checkbox12, R.id.checkbox13, R.id.checkbox14, R.id.checkbox15,
                R.id.checkbox16, R.id.checkbox17, R.id.checkbox18, R.id.checkbox19, R.id.checkbox20,
                R.id.checkbox21, R.id.checkbox22, R.id.checkbox23, R.id.checkbox24, R.id.checkbox25
        };

        //Inicializar el botón continuar
        Button continueButton;
        continueButton = findViewById(R.id.button_continuar);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder selectedSeats = new StringBuilder();
                for(int id: checkBoxIds) {
                    CheckBox checkBox = findViewById(id);
                    if (checkBox.isChecked()){
                        CharSequence contentDescription = checkBox.getContentDescription();
                        selectedSeats.append(contentDescription).append("  ");
                    }
                }

                if (selectedSeats.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes eleccionar al menos un asiento.", Toast.LENGTH_SHORT).show();
                } else {
                    // Pasar la informaciòn a la siguiente actividad
                    Intent i = new Intent(SeatActivity.this, ConfirmationActivity.class);
                    i.putExtra("CINE", cine);
                    i.putExtra("DATE", date);
                    i.putExtra("SCHEDULE", schedule);
                    i.putExtra("SEATS", selectedSeats.toString());
                    i.putExtra("MOVIE_ID", movieId);
                    startActivity(i);
                }


            }
        });


        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SeatActivity.this, CarteleraActivity.class);
                startActivity(i);
            }
        });
    }
}