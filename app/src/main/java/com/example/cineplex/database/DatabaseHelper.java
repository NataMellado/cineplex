package com.example.cineplex.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cineplex.R;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.Ticket;
import com.example.cineplex.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "cineplex.db";
    private static final int DATABASE_VERSION = 14;

    // Instancia estática única de la clase DatabaseHelper
    private static DatabaseHelper instance;

    // Constructor privado para evitar instanciación externa
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método para obtener la única instancia de DatabaseHelper (patrón Singleton)
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            // Crear la instancia si aún no ha sido creada
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    // Método que se ejecuta cuando se crea la base de datos por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tabla users
        String CREATE_USERS_TABLE = "CREATE TABLE users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT UNIQUE, "
                + "rut TEXT, "
                + "password TEXT)";

        // Tabla tickets
        String CREATE_TICKET_TABLE = "CREATE TABLE tickets (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "movie_title TEXT, " +
                "cine TEXT, " +
                "sala TEXT, " +
                "fecha TEXT, " +
                "hora TEXT, " +
                "asientos TEXT, " +
                "valor REAL," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES users(id))";

        // Tabla movies
        String CREATE_MOVIES_TABLE = "CREATE TABLE movies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "image_path INTEGER,"+
                "title TEXT, " +
                "description TEXT)";


        // Crear tablas
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TICKET_TABLE);
        db.execSQL(CREATE_MOVIES_TABLE);


        // Insertar usuario semilla
        db.execSQL("INSERT INTO users (id, username, rut, password) VALUES (1, 'admin', '8-2', '1234');");

        // Insertar películas
        db.execSQL("INSERT INTO movies (id, image_path, title, description) VALUES (1, " + R.drawable.movie_image1 + ", 'Interestelar', 'En un futuro cercano, la humanidad enfrenta la extinción debido a la falta de recursos. Un grupo de exploradores viaja a través de un agujero de gusano cerca de Saturno para encontrar un nuevo hogar para la humanidad. Esta emocionante odisea espacial combina conceptos científicos con una profunda exploración de la relación padre-hijo.');");
        db.execSQL("INSERT INTO movies (id, image_path, title, description) VALUES (2, " + R.drawable.movie_image2 + ", 'Alien Romulus', 'En esta entrega de la famosa saga Alien, un grupo de colonos en un planeta lejano se enfrenta a un aterrador nuevo enemigo. La tensión aumenta mientras los personajes luchan por sobrevivir en un entorno hostil, mientras una misteriosa fuerza alienígena acecha en la oscuridad. Una mezcla perfecta de terror y ciencia ficción.');");
        db.execSQL("INSERT INTO movies (id, image_path, title, description) VALUES (3, " + R.drawable.movie_image3 + ", 'Inception', 'Dom Cobb es un ladrón experto en el arte de la extracción, que roba secretos de los sueños de las personas. Cuando se le ofrece la oportunidad de borrar su pasado criminal a cambio de un trabajo único: la ''incepción'', el acto de implantar una idea en la mente de alguien. A medida que la línea entre la realidad y el sueño se difumina, se desarrolla una intensa batalla por la mente.');");
        db.execSQL("INSERT INTO movies (id, image_path, title, description) VALUES (4, " + R.drawable.movie_image4 + ", 'El señor de los anillos', 'Basada en la famosa obra de J.R.R. Tolkien, esta épica trilogía sigue la aventura de Frodo Bolsón, quien se embarca en un peligroso viaje para destruir un anillo poderoso. Con la ayuda de un grupo diverso de compañeros, se enfrenta a fuerzas oscuras en un mundo de fantasía lleno de magia, amistad y sacrificio.');");
        db.execSQL("INSERT INTO movies (id, image_path, title, description) VALUES (5, " + R.drawable.movie_image5 + ", 'It Ends With Us', 'Una conmovedora historia de amor y autodescubrimiento, donde Lily Bloom, una joven emprendedora, se enfrenta a las complejidades de una relación apasionada. Mientras lucha por construir su vida en Boston, debe enfrentar su oscuro pasado y tomar decisiones difíciles que cambiarán su vida para siempre.');");
        db.execSQL("INSERT INTO movies (id, image_path, title, description) VALUES (6, " + R.drawable.movie_image6 + ", 'Dune II', 'La continuación del aclamado Dune, esta película sigue la lucha de Paul Atreides mientras busca venganza contra aquellos que destruyeron a su familia. Con impresionantes paisajes de ciencia ficción y una profunda exploración de temas como el poder y la supervivencia, Dune II ofrece una experiencia cinematográfica inolvidable.');");
    }

    // Método que se ejecuta cuando se actualiza la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si la base de datos ya existe y ha cambiado de versión, eliminar las tablas
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS tickets");
        db.execSQL("DROP TABLE IF EXISTS movies");

        // Crear nuevamente la base de datos
        onCreate(db);
    }

    // Método para verificar las credenciales del usuario
    public boolean verifyCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean exists = (cursor.getCount() > 0);  // Si el cursor tiene filas, el usuario existe
        if(exists) {
            cursor.moveToFirst();
            // Crear un objeto User con los datos del usuario
            User user = new User(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
            // Establecer el usuario actual en la clase User (Singleton)
            User.setCurrentUser(user);
        }

        cursor.close();
        db.close();
        return exists;
    }


    // Metodo para añadir un usuario
    public long addUser(String username, String rut, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("rut", rut);
        values.put("password", password);

        long userID = db.insert("users", null, values);

        db.close();
        return userID;
    }

    // Metodo para verificar si el RUT ya existe
    public boolean rutExists(String rut) {
        SQLiteDatabase db = this.getReadableDatabase(); // Obtener la base de datos en modo lectura
        String query = "SELECT * FROM users WHERE rut = ?";
        Cursor cursor = db.rawQuery(query, new String[]{rut}); // Ejecutar la consulta SQL con el RUT como parámetro
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    // Metodo para verificar si el nombre de usuario existe
    public boolean usernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE username = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }


    // Método para añadir un ticket
    public boolean addTicket(Ticket ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("movie_title", ticket.getPelicula().getTitle());
        values.put("cine", ticket.getCine());
        values.put("sala", ticket.getSala());
        values.put("fecha", ticket.getFecha());
        values.put("hora", ticket.getHora());
        values.put("asientos", ticket.getAsientos());
        values.put("valor", ticket.getValor());
        values.put("user_id", ticket.getUserId());

        long result = db.insert("tickets", null, values);
        db.close();

        return result != -1; // Retornar true si la inserción fue exitosa o false en caso contrario
    }

    // Método para obtener película por id
    public Movie getMovieById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Movie movie = null;

        String query = "SELECT * FROM movies WHERE id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            movie = new Movie(cursor.getInt(1), cursor.getString(2), cursor.getString(3));
        }

        cursor.close();
        db.close();

        return movie;
    }

    // Método para obtener tickets de un usuario
    public Cursor getTicketsByUserId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM tickets WHERE user_id = ?";
        return db.rawQuery(query, new String[]{String.valueOf(userId)});
    }

}


