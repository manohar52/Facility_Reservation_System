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

public class FMSearchReservations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmsearch_reservations);

        final EditText etdate = findViewById(R.id.etdate);
        final EditText ettime = findViewById(R.id.ettime);
        final EditText etuname = findViewById(R.id.etuname);
        final Spinner type = findViewById(R.id.sptype);

        Calendar cal = new GregorianCalendar();

        SimpleDateFormat df = new SimpleDateFormat(("MM/dd/yyyy"));
        final String currDate = df.format(cal.getTime());
        etdate.setText(currDate);

        SimpleDateFormat tf = new SimpleDateFormat(("hh:mm aa"));
        String currTime = tf.format(cal.getTime());
        ettime.setText(currTime);

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
                String rTime = ettime.getText().toString();
                String rDate = etdate.getText().toString();
                String uname = etuname.getText().toString();

                Intent intent = new Intent(getApplicationContext(),FMViewReseervationList.class);

                Bundle bundle = new Bundle();
                bundle.putString("rdate",rDate);
                bundle.putString("rtime",rTime);
                bundle.putString("fdesc",fdesc);
                bundle.putString("uname",uname);

                intent.putExtras(bundle);
                startActivity(intent);;
            }
        });
    }
}
