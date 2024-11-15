package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Seat implements Parcelable {
    private boolean available;

    // Default constructor
    public Seat() {}

    // Constructor to initialize 'available' field
    public Seat(boolean available) {
        this.available = available;
    }

    // Getter for 'available'
    public boolean isAvailable() {
        return available;
    }

    // Setter for 'available'
    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Constructor that reads from a Parcel
    protected Seat(Parcel in) {
        available = in.readByte() != 0; // 'true' if the byte read is not 0
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (available ? 1 : 0)); // Write 1 if 'available' is true, else 0
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Seat> CREATOR = new Creator<Seat>() {
        @Override
        public Seat createFromParcel(Parcel in) {
            return new Seat(in);
        }

        @Override
        public Seat[] newArray(int size) {
            return new Seat[size];
        }
    };
}
