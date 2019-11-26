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
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

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

        final String rDate,rTime;
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            rDate = bundle.getString("rdate");
            rTime = bundle.getString("rtime");

            editor.putString("rdate", rDate);
            editor.putString("rtime", rTime);
            editor.commit();
        }else{
            rDate = settings.getString("rdate","");
            rTime = settings.getString("rtime","");
        }
        recyclerView = (RecyclerView) findViewById(R.id.rvuserres);
        String currUsername = settings.getString("username"," ");
        final List<Reservation> resList;
        if (rDate.equals("") || rTime.equals("")){
            resList = Reservation.getAllReservations(getApplicationContext(),currUsername);
        }else{
            resList = Reservation.getAllReservationsByDate(getApplicationContext(),currUsername,rDate,rTime);
        }
       if(resList.isEmpty()){
           Toast toast = Toast.makeText(getApplicationContext(), "No Reservations Found", Toast.LENGTH_SHORT);
           toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
           toast.show();
       }
        Collections.sort(resList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Reservation r1 = (Reservation) o1;
                Reservation r2 = (Reservation) o2;
                String t1 = Reservation.timeConvertTo24(r1.getStime());
                String t2 = Reservation.timeConvertTo24(r2.getStime());

                return t1.compareTo(t2);
//                return r1.getStime().compareTo(r2.getStime());
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
