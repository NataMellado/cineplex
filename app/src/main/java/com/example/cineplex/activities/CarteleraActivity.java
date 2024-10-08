package com.example.cineplex.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cineplex.R;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.User;

import java.util.ArrayList;
import java.util.List;


public class CarteleraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Llamada a la superclase onCreate
        super.onCreate(savedInstanceState);
        // Forzar el uso del modo nocturno
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // Establecer el layout de la actividad
        setContentView(R.layout.activity_cartelera);
        // Configuración de padding para las ventanas
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener el usuario de la actividad anterior
        Intent intent = getIntent();
        User user = intent.getParcelableExtra("USER");

        // Crear una lista de objetos Movie
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(R.drawable.movie_image1, "Interestelar", "En un futuro cercano, la humanidad enfrenta la extinción debido a la falta de recursos. Un grupo de exploradores viaja a través de un agujero de gusano cerca de Saturno para encontrar un nuevo hogar para la humanidad. Esta emocionante odisea espacial combina conceptos científicos con una profunda exploración de la relación padre-hijo." ));
        movies.add(new Movie(R.drawable.movie_image2, "Alien Romulus", "En esta entrega de la famosa saga 'Alien', un grupo de colonos en un planeta lejano se enfrenta a un aterrador nuevo enemigo. La tensión aumenta mientras los personajes luchan por sobrevivir en un entorno hostil, mientras una misteriosa fuerza alienígena acecha en la oscuridad. Una mezcla perfecta de terror y ciencia ficción." ));
        movies.add(new Movie(R.drawable.movie_image3, "Inception", "Dom Cobb es un ladrón experto en el arte de la extracción, que roba secretos de los sueños de las personas. Cuando se le ofrece la oportunidad de borrar su pasado criminal a cambio de un trabajo único: la 'incepción', el acto de implantar una idea en la mente de alguien. A medida que la línea entre la realidad y el sueño se difumina, se desarrolla una intensa batalla por la mente." ));
        movies.add(new Movie(R.drawable.movie_image4, "El señor de los anillos", "Basada en la famosa obra de J.R.R. Tolkien, esta épica trilogía sigue la aventura de Frodo Bolsón, quien se embarca en un peligroso viaje para destruir un anillo poderoso. Con la ayuda de un grupo diverso de compañeros, se enfrenta a fuerzas oscuras en un mundo de fantasía lleno de magia, amistad y sacrificio." ));
        movies.add(new Movie(R.drawable.movie_image5, "It Ends With Us", "Una conmovedora historia de amor y autodescubrimiento, donde Lily Bloom, una joven emprendedora, se enfrenta a las complejidades de una relación apasionada. Mientras lucha por construir su vida en Boston, debe enfrentar su oscuro pasado y tomar decisiones difíciles que cambiarán su vida para siempre." ));
        movies.add(new Movie(R.drawable.movie_image6, "Dune II", "La continuación del aclamado 'Dune', esta película sigue la lucha de Paul Atreides mientras busca venganza contra aquellos que destruyeron a su familia. Con impresionantes paisajes de ciencia ficción y una profunda exploración de temas como el poder y la supervivencia, 'Dune II' ofrece una experiencia cinematográfica inolvidable." ));

        // Crear un arreglo de IDs de CardView
        int[] cardViewIds = {
                R.id.cardViewMovie1,
                R.id.cardViewMovie2,
                R.id.cardViewMovie3,
                R.id.cardViewMovie4,
                R.id.cardViewMovie5,
                R.id.cardViewMovie6
        };

        // Configurar los listeners para cada CardView
        for (int i = 0; i < movies.size(); i++) {
            // Método para configurar el listener de clic para cada CardView
            CardView cardView = findViewById(cardViewIds[i]);
            int finalI = i;
            cardView.setOnClickListener(view -> {
                Intent intent2 = new Intent(CarteleraActivity.this, MovieActivity.class);
                intent2.putExtra("MOVIE", movies.get(finalI));
                intent2.putExtra("USER", user);
                System.out.println("ID del usuario desde movies :" + user.getUserId());

                startActivity(intent2);
            });
        }

    }

    // restablecer datos onResume
}