package com.example.facilityreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import adapters.facilitylistadapter;
import model.Facility;
import model.Reservation;
import model.SysUser;
import recyclerlistener.RecyclerTouchListener;

public class AvailableFacilityList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private facilitylistadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_facility_list);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();

        final String rDate,rTime,fdesc;
        recyclerView = (RecyclerView) findViewById(R.id.rvfacility);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            rDate = bundle.getString("rdate");
            rTime = bundle.getString("rtime");
            fdesc = bundle.getString("fdesc");

            editor.putString("rdate", rDate);
            editor.putString("rtime", rTime);
            editor.putString("fdesc", fdesc);
            editor.commit();
        }else{
            rDate = settings.getString("rdate","");
            rTime = settings.getString("rtime","");
            fdesc = settings.getString("fdesc","");
        }


        final TextView tvdate = findViewById(R.id.tvdate);
        final TextView tvtime = findViewById(R.id.tvtime);
        tvdate.setText(rDate);
        tvtime.setText(rTime);

        final Hashtable<String, Facility> facilities = Facility.getAllFacilities(getApplicationContext());
        final List<Facility> facilitiesCopy = new ArrayList<>();
        Set<String> fkeys = facilities.keySet();
        for(String name:fkeys){
            Facility f = Facility.getInstance(name,getApplicationContext());
            if (f.getFtype().getFdesc().equals(fdesc) && f.isAvailable(rDate,rTime) == true ) {     //f.getVenue() == fven &&
                facilitiesCopy.add(f);
            }
        }

        Collections.sort(facilitiesCopy, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Facility f1 = (Facility) o1;
                Facility f2 = (Facility) o2;
                String fnam1 = f1.getName();
                String fnam2 = f2.getName();

                char[] ascii1 = fnam1.toCharArray();
                char[] ascii2 = fnam2.toCharArray();
                int a1 = 0,a2 = 0;
                for(char ch:ascii1) {
                    a1+=(int)ch;
                }
                for(char ch:ascii2) {
                    a2+=(int)ch;
                }
                System.out.println(a1);
                System.out.println(a2);
                System.out.println(f1.getName().compareTo(f2.getName()));
                return a1 - a2;
//                return f1.getName().compareTo(f2.getName());
            }
        });

        mAdapter = new facilitylistadapter(facilitiesCopy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Facility f = facilitiesCopy.get(position);

                Intent intent = new Intent(getApplicationContext(),FacilityDetailForUser.class);
                Bundle bundle = new Bundle();
                bundle.putString("selectedFacility",f.getName());
                bundle.putString("resDate",rDate);
                bundle.putString("resTime",rTime);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
