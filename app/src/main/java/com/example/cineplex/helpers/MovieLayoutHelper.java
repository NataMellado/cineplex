package com.example.cineplex.helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.cineplex.activities.MovieActivity;
import com.example.cineplex.models.Movie;

public class MovieLayoutHelper {

    public void addMovieToLayout(Context context, Movie movie, GridLayout movieContainer, String theaterKey) {
        CardView cardView = new CardView(context);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = 450;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(10, 10, 10, 10);
        cardView.setLayoutParams(params);
        cardView.setRadius(16);
        cardView.setCardElevation(8);

        ImageView movieImage = new ImageView(context);
        movieImage.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        movieImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        int imageResId = context.getResources().getIdentifier(movie.getImage_path(), "drawable", context.getPackageName());
        Glide.with(context).load(imageResId).into(movieImage);

        cardView.addView(movieImage);
        movieContainer.addView(cardView);

        // Agregar el OnClickListener al CardView
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieActivity.class);
            intent.putExtra("theaterKey", theaterKey);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
        });

    }
}
