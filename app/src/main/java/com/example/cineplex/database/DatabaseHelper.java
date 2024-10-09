package com.example.cineplex.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cineplex.models.Ticket;
import com.example.cineplex.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nombre y versión de la base de datos
    private static final String DATABASE_NAME = "cineplex.db";
    private static final int DATABASE_VERSION = 9;

    // Instancia única de DatabaseHelper
    private static DatabaseHelper instance;

    // Constructor privado para evitar la creación directa de instancias
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

        // Crear tablas
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TICKET_TABLE);


        // Insertar usuario semilla
        db.execSQL("INSERT INTO users (id, username, rut, password) VALUES (1, 'admin', '8-2', '1234');");
    }

    // Método que se ejecuta cuando se actualiza la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si la base de datos ya existe y ha cambiado de versión, eliminar las tablas
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS tickets");
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

}


