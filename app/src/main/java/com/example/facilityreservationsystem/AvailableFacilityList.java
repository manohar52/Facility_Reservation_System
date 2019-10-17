package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import adapters.facilitylistadapter;
import model.Facility;
import recyclerlistener.RecyclerTouchListener;

public class AvailableFacilityList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private facilitylistadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_facility_list);

        recyclerView = (RecyclerView) findViewById(R.id.rvfacility);

        Bundle bundle = getIntent().getExtras();
        final String rDate = bundle.getString("rdate");
        final String rTime = bundle.getString("rtime");
        String fdesc = bundle.getString("fdesc");

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
//                TextView tvdate = findViewById(R.id.tvdate);
//                TextView tvtime = findViewById(R.id.tvtime);

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
