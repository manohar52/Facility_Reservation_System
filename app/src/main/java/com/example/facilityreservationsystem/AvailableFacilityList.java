package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import adapters.facilitylistadapter;
import model.Facility;

public class AvailableFacilityList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private facilitylistadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_facility_list);

        recyclerView = (RecyclerView) findViewById(R.id.rvfacility);

        Bundle bundle = getIntent().getExtras();
        String rDate = bundle.getString("rdate");
        String rTime = bundle.getString("rtime");
        String fdesc = bundle.getString("fdesc");

        final TextView tvdate = findViewById(R.id.tvdate);
        final TextView tvtime = findViewById(R.id.tvtime);
        tvdate.setText(rDate);
        tvtime.setText(rTime);

        Hashtable<String, Facility> facilities = Facility.getAllFacilities(getApplicationContext());
        List<Facility> facilitiesCopy = new ArrayList<>();
        Set<String> fkeys = facilities.keySet();
        for(String name:fkeys){
            Facility f = Facility.getInstance(name,getApplicationContext());
            if (f.getFdesc().equals(fdesc) && f.isAvailable(rDate,rTime) == true ) {     //f.getVenue() == fven &&
                facilitiesCopy.add(f);
            }
        }
//        List<Facility> frefs = (List<Facility>) facilities.values();
        mAdapter = new facilitylistadapter(facilitiesCopy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
