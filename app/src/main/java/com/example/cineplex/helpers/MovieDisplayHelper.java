package com.example.cineplex.helpers;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cineplex.models.Movie;

public class MovieDisplayHelper {

    // Método para configurar la imagen, título y descripción de la película
    public static void setupMovieDisplay(Context context, Movie movie, ImageView movieImage, TextView movieTitle, TextView movieDescription) {
        if (movie != null) {
            int imageResId = context.getResources().getIdentifier(movie.getImage_path(), "drawable", context.getPackageName());
            movieImage.setImageResource(imageResId);
            movieTitle.setText(movie.getTitle());

            // Si se proporciona un campo de descripción, configurarlo también
            if (movieDescription != null) {
                movieDescription.setText(movie.getDescription());
            }
        }
    }
}
