package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import adapters.noshowviolationadapter;
import adapters.userreservationadapter;
import model.Reservation;
import recyclerlistener.RecyclerTouchListener;

public class NoShowViolation extends AppCompatActivity {
    private RecyclerView recyclerView;
    private noshowviolationadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_show_violation);

        recyclerView = (RecyclerView) findViewById(R.id.rvnoshowvio);
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = settings.edit();
        String currUsername = settings.getString("username"," ");
        final List<Reservation> resListCopy = new ArrayList<>();
        final List<Reservation> resList = Reservation.getAllReservations(getApplicationContext(),currUsername);
        Iterator<Reservation> iter = resList.iterator();
        while (iter.hasNext()){
            Reservation res = iter.next();
            if (res.getNoshowViolationStatus() != null) {
            resListCopy.add(res);
            }
        }
        Collections.sort(resListCopy, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Reservation r1 = (Reservation) o1;
                Reservation r2 = (Reservation) o2;
                return r1.getDate().compareTo(r2.getDate());
            }
        });
        mAdapter = new noshowviolationadapter(resListCopy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reservation res = resListCopy.get(position);

                Intent intent = new Intent(getApplicationContext(),NoShowViolationDetail.class);
                Bundle bundle = new Bundle();
                bundle.putLong("RESID", res.getResId());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }
}
