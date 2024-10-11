package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.cineplex.R;
import com.example.cineplex.database.DatabaseHelper;
import com.example.cineplex.models.Ticket;

public class SeatActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_seat);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener instancia de DatabaseHelper
        databaseHelper = DatabaseHelper.getInstance(this);

        // Recibir el objeto ticket desde la actividad anterior
        Intent intent = getIntent();
        Ticket ticket = intent.getParcelableExtra("TICKET");


        // Renderizar imagen y título
        ImageView movieImage = findViewById(R.id.movie_image);
        TextView movieTitleView = findViewById(R.id.movie_title);

        // Establecer los valores de imagen y título
        if (ticket != null) {
            movieImage.setImageResource(ticket.getPelicula().getImageResId());
            movieTitleView.setText(ticket.getPelicula().getTitle());
        }

        // Obtener referencia a los CheckBox
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
        continueButton.setOnClickListener(view -> {

            StringBuilder selectedSeats = new StringBuilder();
            int selectedSeatsCount = 0;

            for(int id: checkBoxIds) {
                CheckBox checkBox = findViewById(id);
                if (checkBox.isChecked()){
                    CharSequence contentDescription = checkBox.getContentDescription();
                    selectedSeats.append(contentDescription).append("  ");
                    selectedSeatsCount++;
                }
            }

            if (selectedSeats.toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debes seleccionar al menos un asiento.", Toast.LENGTH_SHORT).show();
            } else {

                // Establecer los asientos seleccionados en el objeto Ticket y calcular el valor
                ticket.setAsientos(selectedSeats.toString());
                ticket.calcularValor(selectedSeatsCount);

                // Añadir el ticket a la base de datos
                boolean isAdded= databaseHelper.addTicket(ticket);

                if (isAdded) {
                    Toast.makeText(getApplicationContext(),  "Ticket comprado con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al comprar el ticket", Toast.LENGTH_SHORT).show();
                }

                // Pasar el objeto Ticket a la nueva Activity de confirmación
                Intent i = new Intent(SeatActivity.this, ConfirmationActivity.class);
                i.putExtra("TICKET", ticket);
                startActivity(i);
            }
        });


        // Configuración del botón Cartelera
        Button carteleraButton = findViewById(R.id.button_cartelera);
        carteleraButton.setOnClickListener(view -> {
            Intent i = new Intent(SeatActivity.this, CarteleraActivity.class);
            startActivity(i);
        });

        // Configuración del botón Profile
        Button profileButton = findViewById(R.id.button_profile);
        profileButton.setOnClickListener(view -> {
            Intent i = new Intent(SeatActivity.this, ProfileActivity.class);
            startActivity(i);
        });

    }
}