package com.example.cineplex.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private long userId;
    private String username;
    private String rut;

    // Variable estática para almacenar el usuario actual
    private static User currentUser;

    // Constructor
    public User(long userId, String username, String rut) {
        this.userId = userId;
        this.username = username;
        this.rut = rut;
    }

    // Getters
    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getRut() {
        return rut;
    }

    // Setters
    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    // M{etodo para establecer el usuario actual
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // M{etodo para obtener el usuario actual
    public static User getCurrentUser() {
        return currentUser;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(userId);
        dest.writeString(username);
        dest.writeString(rut);
    }

    public void readFromParcel(Parcel source) {
        userId = source.readLong();
        username = source.readString();
        rut = source.readString();
    }

    protected User(Parcel in) {
        userId = in.readLong();
        username = in.readString();
        rut = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
