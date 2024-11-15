package com.example.cineplex.helpers;

import android.app.Activity;
import android.content.Intent;

import com.example.cineplex.R;
import com.example.cineplex.activities.CarteleraActivity;
import com.example.cineplex.activities.ProfileActivity;
import com.example.cineplex.activities.TicketsActivity;

public class NavigationHelper {

    public static void setupBottomNavigation(Activity activity) {
        activity.findViewById(R.id.button_profile).setOnClickListener(view ->
                activity.startActivity(new Intent(activity, ProfileActivity.class)));

        activity.findViewById(R.id.button_cartelera).setOnClickListener(view ->
                activity.startActivity(new Intent(activity, CarteleraActivity.class)));

        activity.findViewById(R.id.button_tickets).setOnClickListener(view ->
                activity.startActivity(new Intent(activity, TicketsActivity.class)));
    }
}
