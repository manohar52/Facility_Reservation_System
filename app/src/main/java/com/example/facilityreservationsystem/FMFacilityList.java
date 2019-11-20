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
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import adapters.facilitylistadapter;
import adapters.fmfaclistadapter;
import model.Facility;
import recyclerlistener.RecyclerTouchListener;

public class FMFacilityList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private fmfaclistadapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmfacility_list);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();

        final String fdesc, fname;
        recyclerView = (RecyclerView) findViewById(R.id.rvfmfaclist);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            fdesc = bundle.getString("FDESC");
            fname = bundle.getString("FNAME");


            editor.putString("FDESC", fdesc);
            editor.putString("FNAME", fname);
            editor.commit();
        }else{
            fdesc = settings.getString("FDESC","");
            fname = settings.getString("FNAME","");
        }

        final Hashtable<String, Facility> facilities = Facility.getAllFacilities(getApplicationContext());
        final List<Facility> facilitiesCopy = new ArrayList<>();
        Set<String> fkeys = facilities.keySet();
        for(String name:fkeys){
            Facility f = Facility.getInstance(name,getApplicationContext());
            if (fname.isEmpty()){
                if (f.getFtype().getFdesc().equals(fdesc)){
                    facilitiesCopy.add(f);
                }
            }else{
                if (f.getFtype().getFdesc().equals(fdesc) &&
                        (f.getName().equals(fname))) {
                    facilitiesCopy.add(f);
                }
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

                return a1 - a2;
            }
        });

        mAdapter = new fmfaclistadapter(facilitiesCopy);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Facility f = facilitiesCopy.get(position);

                Intent intent = new Intent(getApplicationContext(),FMFacilityDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("FACNAME",f.getName());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
}
