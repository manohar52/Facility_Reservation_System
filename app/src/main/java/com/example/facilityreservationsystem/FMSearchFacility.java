package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import DOA.facility_doa;

public class FMSearchFacility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmsearch_facility);

        final EditText etfname = findViewById(R.id.etfname);
        final Spinner type = findViewById(R.id.spftype);

        final facility_doa fdoa = facility_doa.getInstance(getApplicationContext());
        final ArrayList<String> facilityTypes = fdoa.getAllFacilityTypes();
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, facilityTypes);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        final Button btSearch = findViewById(R.id.btsearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fdesc = (String) type.getSelectedItem();
                String fname = etfname.getText().toString();

                Intent intent = new Intent(getApplicationContext(),FMFacilityList.class);

                Bundle bundle = new Bundle();
                bundle.putString("FDESC",fdesc);
                bundle.putString("FNAME",fname);

                intent.putExtras(bundle);
                startActivity(intent);;
            }
        });
    }
}
