package com.example.facilityreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import DOA.facility_doa;
import model.Facility;

public class UserFacilitySearch extends AppCompatActivity {

    private String nextSlotTime(String time) {
        String[] comp1 = time.split(":");
        String[] comp2 = comp1[1].split(" ");

        int hh = Integer.parseInt(comp1[0]);
        String mm = comp2[0];
        String aa = comp2[1];

        hh = hh + 1;
        mm = "00";
        switch (aa) {
            case "am":
                if (hh < 6) {
                    hh = 6;
                }
                if (hh == 12) {
                    aa = "pm";
                }
                break;
            case "pm":
                if (hh >= 12) {
                    hh = 6;
                    aa = "am";
                }
        }
        String shh;
        if (hh < 10) {
            shh = String.format("0%s", hh);
        } else {
            shh = String.format("%s", hh);
        }
        return shh + ":" + mm + " " + aa;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_facility_search);

        final EditText etdate = findViewById(R.id.etdate);
        final EditText ettime = findViewById(R.id.ettime);
        final Spinner venue = findViewById(R.id.spvenue);
        final Spinner type = findViewById(R.id.sptype);

        Calendar cal = new GregorianCalendar();

        SimpleDateFormat df = new SimpleDateFormat(("MM/dd/yyyy"));
        String currDate = df.format(cal.getTime());
        etdate.setText(currDate);

        SimpleDateFormat tf = new SimpleDateFormat(("hh:mm aa"));
        String currTime = tf.format(cal.getTime());
        final String nextSlot = nextSlotTime(currTime);
        ettime.setText(nextSlot);

        final facility_doa fdoa = facility_doa.getInstance(getApplicationContext());
        final Hashtable<String, String> facilityTypes = fdoa.getAllFacilityTypes();
        Set<String> keys = facilityTypes.keySet();
//        String[] typeArray = new String[facilityTypes.size()];
        List<String> typeArray = new ArrayList<String>();
//        typeArray = facilityTypes.values().toArray(new String[0]);
        for (String k : keys) {
            typeArray.add(k);
        }
//        typeArray = facilityTypes.keys();
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, typeArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(typeAdapter);

        final Button btSearch = findViewById(R.id.btsearch);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fdesc = (String) type.getSelectedItem();
                String fven = (String) venue.getSelectedItem();
                String rTime = ettime.getText().toString();
                String[] compDate = rTime.split(":");
                String[] compDate1 = compDate[1].split(" ");
                if (compDate1[0] != "00"){
                    rTime = nextSlotTime(rTime);
                }
                String rDate = etdate.getText().toString();

                Intent intent = new Intent(getApplicationContext(),AvailableFacilityList.class);
                Bundle bundle = new Bundle();
                bundle.putString("rdate",rDate);
                bundle.putString("rtime",rTime);
                bundle.putString("fdesc",fdesc);
                intent.putExtras(bundle);
                startActivity(intent);;
            }
        });
    }
}