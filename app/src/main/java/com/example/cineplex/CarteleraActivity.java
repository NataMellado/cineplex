package com.example.cineplex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class CarteleraActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cartelera);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuración del Spinner
        Spinner spinner = findViewById(R.id.header_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.header_spinner_data, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Configuración de los listeners para cada CardView
        setUpCardViewClickListener(R.id.cardViewMovie1, "Movie1");
        setUpCardViewClickListener(R.id.cardViewMovie2, "Movie2");
        setUpCardViewClickListener(R.id.cardViewMovie3, "Movie3");
        setUpCardViewClickListener(R.id.cardViewMovie4, "Movie4");
        setUpCardViewClickListener(R.id.cardViewMovie5, "Movie5");
        setUpCardViewClickListener(R.id.cardViewMovie6, "Movie6");
    }

    // Método para configurar el listener de clic para cada CardView
    private void setUpCardViewClickListener(int cardViewId, final String movieId) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarteleraActivity.this,  MovieActivity.class);
                intent.putExtra("MOVIE_ID", movieId);
                startActivity(intent);
            }
        });
    }
}