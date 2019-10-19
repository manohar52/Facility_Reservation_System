package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;

import java.util.List;

import adapters.facilitylistadapter;
import adapters.userreservationadapter;
import model.Reservation;

public class UserReservationList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private userreservationadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_list);
        recyclerView = (RecyclerView) findViewById(R.id.rvuserres);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = settings.edit();
        String currUsername = settings.getString("username"," ");
        List<Reservation> resList = Reservation.getAllReservations(getApplicationContext(),currUsername);

        mAdapter = new userreservationadapter(resList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
