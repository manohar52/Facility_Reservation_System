package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import adapters.facilitylistadapter;
import adapters.userreservationadapter;
import model.Facility;
import model.Reservation;
import recyclerlistener.RecyclerTouchListener;

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
        final List<Reservation> resList = Reservation.getAllReservations(getApplicationContext(),currUsername);
        Collections.sort(resList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Reservation r1 = (Reservation) o1;
                Reservation r2 = (Reservation) o2;
                return r1.getDate().compareTo(r2.getDate());
            }
        });
        mAdapter = new userreservationadapter(resList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reservation res = resList.get(position);

                Intent intent = new Intent(getApplicationContext(),ReservationDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("RESID", String.valueOf(res.getResId()));
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
