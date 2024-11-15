package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Map;

public class Schedule implements Parcelable {

    // Propiedades
    private Map<String, Map<String, Seat>> times; // Map de horarios con asientos

    // Constructor vacío requerido por Firebase
    public Schedule() {}

    // Constructor principal
    public Schedule(Map<String, Map<String, Seat>> times) {
        this.times = times;
    }

    // Getters y setters
    public void setTimes(Map<String, Map<String, Seat>> times) {
        this.times = times;
    }
    public Map<String, Map<String, Seat>> getTimes() { return times; }

    protected Schedule(Parcel in) {
        times = new HashMap<>();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String time = in.readString();
            Map<String, Seat> seats = in.readHashMap(Seat.class.getClassLoader());
            times.put(time, seats);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(times.size());
        for (Map.Entry<String, Map<String, Seat>> entry : times.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeMap(entry.getValue());
        }
    }

    @Override
    public int describeContents() { return 0; }

    public static final Creator<Schedule> CREATOR = new Creator<Schedule>() {
        @Override
        public Schedule createFromParcel(Parcel in) { return new Schedule(in); }
        @Override
        public Schedule[] newArray(int size) { return new Schedule[size]; }
    };
}
