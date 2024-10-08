package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ticket implements Parcelable {
    private Movie pelicula;
    private String cine;
    private String sala;
    private String fecha;
    private String hora;
    private String asientos;
    private double valor;
    private long userId;

    // Constructor
    public Ticket(Movie pelicula, String cine, String sala, String fecha, String hora, String asientos, double valor, long userId) {
        this.pelicula = pelicula;
        this.cine = cine;
        this.sala = sala;
        this.fecha = fecha;
        this.hora = hora;
        this.asientos = asientos;
        this.valor = valor;
        this.userId = userId;
    }

    // Getters
    public Movie getPelicula() {
        return pelicula;
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

    public String getAsientos() {
        return asientos;
    }

    public double getValor() {
        return valor;
    }

    public long getUserId() {
        return userId;
    }

    // Setters
    public void setPelicula(Movie pelicula) {
        this.pelicula = pelicula;
    }

    public void setCine(String cine) {
        this.cine = cine;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setAsientos(String asientos) {
        this.asientos = asientos;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void calcularValor(int cantidad) {
        this.valor = cantidad * 5990;
    }

    // Implementación de Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(pelicula, flags);
        dest.writeString(cine);
        dest.writeString(sala);
        dest.writeString(fecha);
        dest.writeString(hora);
        dest.writeString(asientos);
        dest.writeDouble(valor);
        dest.writeLong(userId);
    }

    protected Ticket(Parcel in) {
        pelicula = in.readParcelable(Movie.class.getClassLoader());
        cine = in.readString();
        sala = in.readString();
        fecha = in.readString();
        hora = in.readString();
        asientos = in.readString();
        valor = in.readDouble();
        userId = in.readLong();
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
