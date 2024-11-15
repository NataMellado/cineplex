package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;

public class Cine implements Parcelable {

    // Propiedades
    private String name;
    private String location;
    private List<Movie> movies;

    // Constructor vacío requerido por Firebase
    public Cine() {
        movies = new ArrayList<>();
    }

    // Constructor principal
    public Cine(String name, String location, List<Movie> movies) {
        this.name = name;
        this.location = location;
        this.movies = movies != null ? movies : new ArrayList<>();
    }

    // Getters y Setters
    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Movie> getMovies() { return movies; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }

    // Métodos Parcelable
    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(location);
        dest.writeTypedList(movies);
    }

    // Constructor protegido para deserialización
    protected Cine(Parcel in) {
        name = in.readString();
        location = in.readString();
        movies = in.createTypedArrayList(Movie.CREATOR);
    }

    // Creator para Parcelable
    public static final Creator<Cine> CREATOR = new Creator<Cine>() {
        @Override
        public Cine createFromParcel(Parcel source) { return new Cine(source); }

        @Override
        public Cine[] newArray(int size) { return new Cine[size]; }
    };

    // Método toString para mostrar el nombre del cine
    @Override
    public String toString() { return name; }
}
