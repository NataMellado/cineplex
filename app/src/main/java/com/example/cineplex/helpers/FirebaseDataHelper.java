package com.example.cineplex.helpers;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.cineplex.models.Ticket;
import com.example.cineplex.models.Movie;
import com.example.cineplex.models.Schedule;
import com.example.cineplex.models.Seat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDataHelper {

    // Referencia a la base de datos de Firebase
    private final DatabaseReference theatersRef = FirebaseDatabase.getInstance().getReference("theaters");
    private final DatabaseReference ticketsRef = FirebaseDatabase.getInstance().getReference("tickets");
    private final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    // Interfaz para notificar cuando los datos han sido cargados
    public interface DataLoadListener {
        void onDataLoaded();
    }

    // Interfaz para notificar cuando los detalles de la película han sido cargados
    public interface MovieDetailListener {
        void onMovieDetailsLoaded(Movie movie);
    }

    // Interfaz para notificar cuando las películas han sido cargadas
    public interface MovieListLoadListener {
        void onMoviesLoaded(List<Movie> movies);
    }

    // Interfaz para notificar cuando los asientos han sido cargados
    public interface SeatLoadListener {
        void onSeatsLoaded(Map<String, Seat> seats);
    }

    // Interfaz para notificar cuando un usuario es creado
    public interface UserCreationListener {
        void onUserCreated();
        void onError(String error);
    }

    // Interfaz para cargar tickets
    public interface TicketsLoadListener {
        void onTicketsLoaded(List<Ticket> tickets);
        void onError(String error);
    }

    // Interfaz para manejar la eliminación de un ticket
    public interface TicketDeletionListener {
        void onTicketDeleted();

        void onError(String error);
    }


    // CRUD-READ: Método para obtener cines desde Firebase
    public void loadTheatersFromFirebase(Context context, List<String> theaterDisplayNames, HashMap<String, String> theaterMap, ArrayAdapter<String> theaterAdapter, DataLoadListener listener) {
        theatersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                theaterDisplayNames.clear();
                theaterMap.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String theaterKey = snapshot.getKey();
                    String theaterName = snapshot.child("name").getValue(String.class);
                    if (theaterKey != null && theaterName != null) {
                        theaterDisplayNames.add(theaterName);
                        theaterMap.put(theaterName, theaterKey);
                    }
                }
                theaterAdapter.notifyDataSetChanged();
                // Notificar que los datos han sido cargados
                listener.onDataLoaded();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error al cargar cines", Toast.LENGTH_SHORT).show();
                listener.onDataLoaded();
            }
        });
    }

    // CRUD-READ: Método para obtener todas las películas de un cine específico
    public void loadMoviesForSelectedTheater(String selectedTheaterKey, Context context, MovieListLoadListener listener) {
        if (selectedTheaterKey == null) return;

        DatabaseReference moviesRef = theatersRef.child(selectedTheaterKey).child("movies");
        moviesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Movie> movies = new ArrayList<>(); // Lista temporal para almacenar películas
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Movie movie = snapshot.getValue(Movie.class);
                    if (movie != null) {
                        movies.add(movie);
                    }
                }
                listener.onMoviesLoaded(movies); // Pasar la lista a través de la interfaz al finalizar
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error al cargar películas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // CRUD-READ: Método para obtener los detalles de una película específica (incluyendo fechas, horarios y asientos)
    public void loadMovieDetails(String theaterKey, String movieKey, Context context, MovieDetailListener listener) {
        if (theaterKey == null || movieKey == null) return;

        DatabaseReference movieRef = theatersRef.child(theaterKey).child("movies").child(movieKey);
        movieRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Cargar los detalles de la película
                Movie movie = dataSnapshot.getValue(Movie.class);
                if (movie != null) {
                    // Procesar las fechas y horarios desde la estructura de datos de Firebase
                    HashMap<String, Schedule> dates = new HashMap<>();

                    for (DataSnapshot dateSnapshot : dataSnapshot.child("dates").getChildren()) {
                        String date = dateSnapshot.getKey();
                        HashMap<String, Map<String, Seat>> timeSlots = new HashMap<>();

                        // Para cada horario en la fecha
                        for (DataSnapshot timeSnapshot : dateSnapshot.child("times").getChildren()) {
                            String time = timeSnapshot.getKey();
                            HashMap<String, Seat> seats = new HashMap<>();

                            // Para cada asiento en el horario
                            for (DataSnapshot seatSnapshot : timeSnapshot.child("seats").getChildren()) {
                                String seatNumber = seatSnapshot.getKey();
                                Seat seat = seatSnapshot.getValue(Seat.class);
                                seats.put(seatNumber, seat);
                            }

                            // Asigna los asientos al horario correspondiente
                            timeSlots.put(time, seats);
                        }
                        // Asigna el horario al día correspondiente
                        Schedule schedule = new Schedule();
                        schedule.setTimes(timeSlots); // Ahora `times` es un Map<String, Map<String, Seat>>
                        dates.put(date, schedule);
                    }

                    // Asigna el mapa de fechas a la película
                    movie.setDates(dates); // Usar 'setDates' en lugar de 'setSchedule'

                    // Notificar que los detalles de la película han sido cargados
                    listener.onMovieDetailsLoaded(movie);
                } else {
                    Toast.makeText(context, "Error al cargar los detalles de la película", Toast.LENGTH_SHORT).show();
                    listener.onMovieDetailsLoaded(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error al cargar los detalles de la película", Toast.LENGTH_SHORT).show();
                listener.onMovieDetailsLoaded(null);
            }
        });
    }

    // CRUD-READ: Método para obtener los asientos disponibles para un horario específico
    public void loadSeatsForSchedule(String theaterKey, String movieKey, String date, String time, SeatLoadListener listener) {
        DatabaseReference seatsRef = theatersRef.child(theaterKey).child("movies").child(movieKey)
                .child("dates").child(date).child("times").child(time).child("seats");

        seatsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Seat> seats = new HashMap<>();
                for (DataSnapshot seatSnapshot : dataSnapshot.getChildren()) {
                    Seat seat = seatSnapshot.getValue(Seat.class);
                    if (seat != null) {
                        seats.put(seatSnapshot.getKey(), seat);
                    }
                }
                listener.onSeatsLoaded(seats);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejo de errores
            }
        });
    }

    // CRUD-CREATE: Método para crear un ticket en Firebase
    public void createTicket(Ticket ticket) {
        String ticketId = ticketsRef.push().getKey(); // Genera un ID único para el ticket

        if (ticketId != null) {
            ticketsRef.child(ticketId).setValue(ticket)
                    .addOnSuccessListener(aVoid -> System.out.println("Ticket creado exitosamente en Firebase."))
                    .addOnFailureListener(e -> System.err.println("Error al crear el ticket: " + e.getMessage()));
        } else {
            System.err.println("Error al generar el ID del ticket.");
        }
    }

    // CRUD-CREATE: Método para crear un usuario en Firebase
    public void createUser(String userId, String email, String rut, UserCreationListener listener) {
        DatabaseReference userRef = usersRef.child(userId);
        userRef.child("email").setValue(email);
        userRef.child("rut").setValue(rut).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onUserCreated();
            } else {
                listener.onError("Error al crear el usuario: " + task.getException().getMessage());
            }
        });
    }

    // CRUD-UPDATE: Método para actualizar el estado de un asiento en Firebase
    public void updateSeatStatus(String theaterKey, String movieKey, String date, String time, List<String> seatNumbers) {
        DatabaseReference seatsRef = FirebaseDatabase.getInstance().getReference("theaters")
                .child(theaterKey)
                .child("movies")
                .child(movieKey)
                .child("dates")
                .child(date)
                .child("times")
                .child(time)
                .child("seats");

        for (String seatNumber : seatNumbers) {
            seatsRef.child(seatNumber).child("available").setValue(false)
                    .addOnSuccessListener(aVoid -> System.out.println("Asiento " + seatNumber + " actualizado como ocupado."))
                    .addOnFailureListener(e -> System.err.println("Error al actualizar el asiento " + seatNumber + ": " + e.getMessage()));
        }
    }

    // CRUD-READ: Método para obtener tickets de un usuario específico
    public void getTicketsForUser(String userId, TicketsLoadListener listener) {
        ticketsRef.orderByChild("userId").equalTo(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Ticket> tickets = new ArrayList<>();
                        for (DataSnapshot ticketSnapshot : dataSnapshot.getChildren()) {
                            Ticket ticket = ticketSnapshot.getValue(Ticket.class);
                            if (ticket != null) {
                                tickets.add(ticket);
                            }
                        }
                        listener.onTicketsLoaded(tickets);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onError("Error al cargar los tickets: " + databaseError.getMessage());
                    }
                });
    }

    // CRUD-DELETE: Método para eliminar todos los tickets de un usuario
    public void deleteAllTicketsForUser(String userId, TicketDeletionListener listener) {
        // Referencia a la base de datos de los tickets
        DatabaseReference userTicketsRef = ticketsRef;

        // Crear una consulta para obtener solo los tickets del usuario con el userId especificado
        userTicketsRef.orderByChild("userId").equalTo(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Eliminar todos los tickets del usuario
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                    }
                    listener.onTicketDeleted();
                } else {
                    listener.onError("No se encontraron tickets para este usuario.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onError("Error al eliminar los tickets: " + databaseError.getMessage());
            }
        });
    }






}
