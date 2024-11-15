package com.example.cineplex.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cineplex.R;
import com.example.cineplex.helpers.FirebaseDataHelper;
import com.example.cineplex.helpers.NavigationHelper;
import com.example.cineplex.models.Ticket;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class TicketsActivity extends AppCompatActivity {

    private ListView ticketsListView;
    private ArrayAdapter<Ticket> ticketAdapter;
    private FirebaseDataHelper firebaseDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        NavigationHelper.setupBottomNavigation(this);

        ticketsListView = findViewById(R.id.ticketsListView);
        Button deleteHistoryButton = findViewById(R.id.deleteHistoryButton);

        firebaseDataHelper = new FirebaseDataHelper();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Cargar los tickets del usuario
        firebaseDataHelper.getTicketsForUser(userId, new FirebaseDataHelper.TicketsLoadListener() {
            @Override
            public void onTicketsLoaded(List<Ticket> tickets) {
                ticketAdapter = new ArrayAdapter<>(TicketsActivity.this,
                        android.R.layout.simple_list_item_2, android.R.id.text1, tickets);
                ticketsListView.setAdapter(ticketAdapter);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(TicketsActivity.this, "Error al cargar los tickets", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el clic en el botón para eliminar el historial
        deleteHistoryButton.setOnClickListener(view -> {
            // Llamar al método para eliminar todos los tickets del usuario
            firebaseDataHelper.deleteAllTicketsForUser(userId, new FirebaseDataHelper.TicketDeletionListener() {
                @Override
                public void onTicketDeleted() {
                    // Limpiar el ListView y mostrar un mensaje de éxito
                    ticketAdapter.clear();
                    Toast.makeText(TicketsActivity.this, "Historial de tickets eliminado", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(TicketsActivity.this, "Error al eliminar los tickets: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });


    }
}
