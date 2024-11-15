package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

public class Movie implements Parcelable {
    // Propiedades
    private String id;
    private String image_path;
    private String title;
    private String description;
    private Map<String, Schedule> dates;

    // Constructor vacío requerido por Firebase
    public Movie() {}

    // Constructor principal
    public Movie(String id, String image_path, String title, String description, Map<String, Schedule> dates) {
        this.id = id;
        this.image_path = image_path;
        this.title = title;
        this.description = description;
        this.dates = dates;
    }

    // Métodos getter y setter
    public String getId() { return id; }  // Nuevo getter para id
    public void setId(String id) { this.id = id; }  // Setter para id
    public String getImage_path() { return image_path; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Map<String, Schedule> getDates() { return dates; }
    public void setDates(Map<String, Schedule> dates) { this.dates = dates; }


    // Métodos Parcelable
    protected Movie(Parcel in) {
        id = in.readString();
        image_path = in.readString();
        title = in.readString();
        description = in.readString();
        dates = in.readHashMap(Schedule.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(image_path);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeMap(dates);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) { return new Movie(in); }
        @Override
        public Movie[] newArray(int size) { return new Movie[size]; }
    };
}
