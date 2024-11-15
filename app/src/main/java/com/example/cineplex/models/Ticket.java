package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Ticket implements Parcelable {
    private String id;               // Nuevo campo para el ID del ticket
    private String movieId;
    private String movieTitle;
    private String movieImagePath;
    private String cine;
    private String sala;
    private String fecha;
    private String hora;
    private List<String> asientos;
    private double valor;
    private String userId;

    // Constructor vacío
    public Ticket() {
    }

    // Constructor
    public Ticket(String id, String movieId, String movieTitle, String movieImagePath, String cine, String sala,
                  String fecha, String hora, List<String> asientos, double valor, String userId) {
        this.id = id;                   // Agregar el ID al constructor
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieImagePath = movieImagePath;
        this.cine = cine;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.asientos = asientos;
        this.valor = valor;
        this.userId = userId;
    }

    // Getters
    public String getId() {
        return id;                       // Retornar el ID
    }

    public void setId(String id) {
        this.id = id;                   // Establecer el ID
    }

    public String getMovieId() {
        return movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMovieImagePath() {
        return movieImagePath;
    }

    public String getCine() {
        return cine;
    }

    public String getSala() {
        return sala;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public List<String> getAsientos() {
        return asientos;
    }

    public double getValor() {
        return valor;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Película: " + movieTitle + "\n" +
                "Cine" + cine + "\n" +
                "Fecha: " + fecha + "\n" +
                "Hora: " + hora + "\n" +
                "Asientos: " + asientos + "\n" +
                "Valor: " + valor;
    }

    // Implementación de Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);                       // Escribir el ID
        dest.writeString(movieId);
        dest.writeString(movieTitle);
        dest.writeString(movieImagePath);
        dest.writeString(cine);
        dest.writeString(sala);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeStringList(asientos);
        dest.writeDouble(valor);
        dest.writeString(userId);
    }

    protected Ticket(Parcel in) {
        id = in.readString();                       // Leer el ID
        movieId = in.readString();
        movieTitle = in.readString();
        movieImagePath = in.readString();
        cine = in.readString();
        sala = in.readString();
        fecha = in.readString();
        hora = in.readString();
        asientos = in.createStringArrayList();
        valor = in.readDouble();
        userId = in.readString();
    }

    public static final Parcelable.Creator<Ticket> CREATOR = new Parcelable.Creator<Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel source) {
            return new Ticket(source);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };
}
