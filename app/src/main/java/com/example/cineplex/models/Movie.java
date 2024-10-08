package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private int image;
    private String title;
    private String description;

    // Constructor
    public Movie(int image, String title, String description) {
        this.image = image;
        this.title = title;
        this.description = description;
    }

    public int getImageResId() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setImageResId(int imageResId) {
        this.image = imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(title);
        dest.writeString(description);
    }

    protected Movie(Parcel in) {
        image = in.readInt();
        title = in.readString();
        description = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
