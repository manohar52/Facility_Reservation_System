package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import adapters.facilitylistadapter;
import adapters.fmreslistadapter;
import model.Facility;
import model.Reservation;
import recyclerlistener.RecyclerTouchListener;

public class FMViewReseervationList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private fmreslistadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmview_reseervation_list);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();

        final String rDate,rTime,fdesc, uname;
        recyclerView = (RecyclerView) findViewById(R.id.rvfmreslist);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            rDate = bundle.getString("rdate");
            rTime = bundle.getString("rtime");
            fdesc = bundle.getString("fdesc");
            uname = bundle.getString("uname");

            editor.putString("rdate", rDate);
            editor.putString("rtime", rTime);
            editor.putString("fdesc", fdesc);
            editor.putString("uname", uname);
            editor.commit();
        }else{
            rDate = settings.getString("rdate","");
            rTime = settings.getString("rtime","");
            fdesc = settings.getString("fdesc","");
            uname = settings.getString("uname","");
        }


        final List<Reservation> resList = Reservation.getFMReservations(getApplicationContext(),rDate,rTime,fdesc,uname);
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

                String fnam1 = r1.getFacName();
                String fnam2 = r2.getFacName();

                char[] ascii1 = fnam1.toCharArray();
                char[] ascii2 = fnam2.toCharArray();
                int a1 = 0,a2 = 0;
                for(char ch:ascii1) {
                    a1+=(int)ch;
                }
                for(char ch:ascii2) {
                    a2+=(int)ch;
                }
                return a1 - a2;

            }
        });

        mAdapter = new fmreslistadapter(resList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Reservation res = resList.get(position);

                Intent intent = new Intent(getApplicationContext(),FMReservationDetail.class);
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
