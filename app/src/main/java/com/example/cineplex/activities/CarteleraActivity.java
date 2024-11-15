package com.example.cineplex.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.cineplex.R;
import com.example.cineplex.helpers.FirebaseDataHelper;
import com.example.cineplex.helpers.LoadingHelper;
import com.example.cineplex.helpers.MovieLayoutHelper;
import com.example.cineplex.helpers.NavigationHelper;
import com.example.cineplex.models.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CarteleraActivity extends AppCompatActivity {

    private FirebaseDataHelper firebaseDataHelper;
    private MovieLayoutHelper movieLayoutHelper;
    private List<String> theaterDisplayNames = new ArrayList<>();
    private HashMap<String, String> theaterMap = new HashMap<>();
    private ArrayAdapter<String> theaterAdapter;
    private Spinner theaterSpinner;
    private GridLayout movieContainer;
    private String selectedTheaterKey;
    private List<Movie> movieList = new ArrayList<>();
    private ProgressBar loadingSpinner;
    private ProgressBar loadingSpinnerSpinner;
    private LoadingHelper loadingHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_cartelera);
        NavigationHelper.setupBottomNavigation(this);

        firebaseDataHelper = new FirebaseDataHelper();
        movieLayoutHelper = new MovieLayoutHelper();

        movieContainer = findViewById(R.id.movie_container);
        loadingSpinner = findViewById(R.id.loading_spinner);
        loadingSpinnerSpinner = findViewById(R.id.loading_spinner_spinner);
        loadingHelper = new LoadingHelper(loadingSpinner);

        setupTheaterSpinner();
    }

    // Método para configurar el Spinner de cines
    private void setupTheaterSpinner() {
        theaterSpinner = findViewById(R.id.header_spinner);
        RelativeLayout spinnerContainer = findViewById(R.id.spinner_container);


        // Configurar el adaptador para el Spinner
        theaterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theaterDisplayNames);
        theaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theaterSpinner.setAdapter(theaterAdapter);

        // Ocultar el contenedor del Spinner
        spinnerContainer.setVisibility(View.GONE);

        // Mostrar progressBar de carga para el Spinner de cine
        loadingSpinnerSpinner.setVisibility(View.VISIBLE);

        // Cargar los datos de los cines
        firebaseDataHelper.loadTheatersFromFirebase(this, theaterDisplayNames, theaterMap, theaterAdapter, new FirebaseDataHelper.DataLoadListener() {
            @Override
            public void onDataLoaded() {
                loadingHelper.hideLoading(loadingSpinnerSpinner);
                spinnerContainer.setVisibility(View.VISIBLE);
            }
        });

        //  Manejar la selección del Spinner de cines, para cargar películas correspondientes
        theaterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTheaterKey = theaterMap.get(theaterDisplayNames.get(position));
                loadingHelper.showLoading(loadingSpinner);
                movieContainer.setVisibility(View.GONE);

                movieList.clear();
                movieContainer.removeAllViews();

                // Cargar películas para el cine seleccionado
                firebaseDataHelper.loadMoviesForSelectedTheater(selectedTheaterKey, CarteleraActivity.this, new FirebaseDataHelper.MovieListLoadListener() {
                    @Override
                    public void onMoviesLoaded(List<Movie> movies) {
                        loadingHelper.hideLoading(loadingSpinner);
                        movieContainer.setVisibility(View.VISIBLE);

                        // Añadir las películas al contenedor usando el helper
                        for (Movie movie : movies) {
                            movieLayoutHelper.addMovieToLayout(CarteleraActivity.this, movie, movieContainer, selectedTheaterKey);
                        }
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

}
